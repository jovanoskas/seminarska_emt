package com.example.seminarska_emt.Service.Implementation;

import com.example.seminarska_emt.Repository.ShoppingCartRepository;
import com.example.seminarska_emt.Service.PaymentService;
import com.example.seminarska_emt.Service.ShoppingCartService;
import com.example.seminarska_emt.Service.SongService;
import com.example.seminarska_emt.Service.UserService;
import com.example.seminarska_emt.constraint.ChargeRequest;
import com.example.seminarska_emt.model.Enumerations.CartStatus;
import com.example.seminarska_emt.model.ShoppingCart;
import com.example.seminarska_emt.model.Song;
import com.example.seminarska_emt.model.User;
import com.example.seminarska_emt.model.exceptions.ShoppingCartIsAlreadyCreated;
import com.example.seminarska_emt.model.exceptions.ShoppingCartIsNotActive;
import com.example.seminarska_emt.model.exceptions.SongIsAlreadyInShoppingCart;
import com.example.seminarska_emt.model.exceptions.TransactionFailed;
import com.stripe.exception.*;
import com.stripe.model.Charge;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ShoppingCartServiceImpl implements ShoppingCartService {
    private final UserService userService;
    private final SongService songService;
    private final ShoppingCartRepository shoppingCartRepository;
    private final PaymentService paymentService;

    public ShoppingCartServiceImpl(UserService userService,
                                   SongService songService,
                                   ShoppingCartRepository shoppingCartRepository,
                                   PaymentService paymentService) {
        this.userService = userService;
        this.songService = songService;
        this.shoppingCartRepository = shoppingCartRepository;
        this.paymentService = paymentService;
    }


    @Override
    public List<ShoppingCart> findAllByUsername(String userId) {
        return this.shoppingCartRepository.findAllByUserUsername(userId);
    }


   @Override
    public ShoppingCart createNewShoppingCart(String userId) {
        User user = this.userService.findById(userId);
        if (this.shoppingCartRepository.existsByUserUsernameAndStatus(
                user.getUsername(),
                CartStatus.CREATED
        )) {
            throw new ShoppingCartIsAlreadyCreated(userId);
        }
        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setUser(user);
        return this.shoppingCartRepository.save(shoppingCart);
    }

    public ShoppingCart addSongToShoppingCart(String userId, Long productId) {
        ShoppingCart shoppingCart = this.getActiveShoppingCart(userId);
        Song song = this.songService.findById(productId);
        for (Song s : shoppingCart.getSongs()) {
            if (s.getId().equals(productId)) {
                throw new SongIsAlreadyInShoppingCart(song.getName());
            }
        }
        shoppingCart.getSongs().add(song);
        return this.shoppingCartRepository.save(shoppingCart);
    }

    @Override
    public ShoppingCart removeSongFromShoppingCart(String userId, Long songId) {
        ShoppingCart shoppingCart = this.getActiveShoppingCart(userId);
        shoppingCart.setSongs(
                shoppingCart.getSongs()
                        .stream()
                        .filter(song -> !song.getId().equals(songId))
                        .collect(Collectors.toList())
        );
        return this.shoppingCartRepository.save(shoppingCart);
    }

    @Override
    public ShoppingCart getActiveShoppingCart(String userId) {
        return this.shoppingCartRepository
                .findByUserUsernameAndStatus(userId,CartStatus.CREATED)
                .orElseGet(()-> {
                    ShoppingCart shoppingCart = new ShoppingCart();
                    User user = this.userService.findById(userId);
                    shoppingCart.setUser(user);
                    return this.shoppingCartRepository.save(shoppingCart);
        });
    }


    @Override
    public ShoppingCart cancelActiveShoppingCart(String userId) {
        ShoppingCart shoppingCart = this.shoppingCartRepository
                .findByUserUsernameAndStatus(userId, CartStatus.CREATED)
                .orElseThrow(() -> new ShoppingCartIsNotActive(userId));
        shoppingCart.setStatus(CartStatus.CANCELED);
        return this.shoppingCartRepository.save(shoppingCart);
    }

    @Override
    @Transactional
    public ShoppingCart checkoutShoppingCart(String userId, ChargeRequest chargeRequest) {
        ShoppingCart shoppingCart = this.shoppingCartRepository
                .findByUserUsernameAndStatus(userId, CartStatus.CREATED)
                .orElseThrow(() -> new ShoppingCartIsNotActive(userId));

        List<Song> songs = shoppingCart.getSongs();
        float price = 0;

        for (Song song : songs) {
            price += song.getPrice();
        }
        Charge charge = null;
        try {
            charge = this.paymentService.pay(chargeRequest);
        } catch (CardException | APIException | AuthenticationException | APIConnectionException | InvalidRequestException e) {
            throw new TransactionFailed(userId, e.getMessage());
        }

        shoppingCart.setSongs(songs);
        shoppingCart.setStatus(CartStatus.FINISHED);
        return this.shoppingCartRepository.save(shoppingCart);

    }


}
