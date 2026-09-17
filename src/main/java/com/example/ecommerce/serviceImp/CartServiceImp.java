package com.example.ecommerce.serviceImp;

import com.example.ecommerce.dto.CartRequest;
import com.example.ecommerce.dto.CartResponse;
import com.example.ecommerce.exception.ResourceNotFoundException;
import com.example.ecommerce.model.Cart;
import com.example.ecommerce.model.Custmer;
import com.example.ecommerce.model.Product;
import com.example.ecommerce.repo.CartRepo;
import com.example.ecommerce.repo.CustmRepo;
import com.example.ecommerce.repo.ProductRepo;
import com.example.ecommerce.service.CartService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CartServiceImp implements CartService {

    private final CartRepo cartRepo;
    private final CustmRepo custmRepo;
    private final ProductRepo productRepo;
    private final ModelMapper modelMapper;

    @Override
    public CartResponse addToCart(CartRequest request) {

        Custmer custmer = custmRepo.findById(request.getCustomerId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Customer not found with Id : "
                                + request.getCustomerId()));

        Product product = productRepo.findById(request.getProductId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Product not found with Id : "
                                + request.getProductId()));

        Cart cart = new Cart();

        cart.setCustomer(custmer);
        cart.setProduct(product);
        cart.setQuantity(request.getQuantity());

        Cart savedCart = cartRepo.save(cart);

        CartResponse response = new CartResponse();

        response.setId(savedCart.getId());
        response.setCustomerId(custmer.getId());
        response.setProductId(product.getId());
        response.setProductName(product.getProductName());
        response.setPrice(product.getPrice());
        response.setQuantity(savedCart.getQuantity());

        BigDecimal total =
                product.getPrice().multiply(BigDecimal.valueOf(savedCart.getQuantity()));

        response.setTotalPrice(total);

        return response;
    }

    @Override
    @Transactional
    public List<CartResponse> getCustomerCart(Long customerId) {

        Custmer custmer = custmRepo.findById(customerId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Customer not found with Id : "
                                + customerId));

        List<Cart> carts = cartRepo.findByCustomer(custmer);

        return carts.stream().map(cart -> {

            CartResponse response = new CartResponse();

            response.setId(cart.getId());
            response.setCustomerId(custmer.getId());
            response.setProductId(cart.getProduct().getId());
            response.setProductName(cart.getProduct().getProductName());
            response.setPrice(cart.getProduct().getPrice());
            response.setQuantity(cart.getQuantity());

            response.setTotalPrice(
                    cart.getProduct().getPrice()
                            .multiply(BigDecimal.valueOf(cart.getQuantity()))
            );

            return response;

        }).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public CartResponse updateCart(Long cartId, CartRequest request) {

        Cart cart = cartRepo.findById(cartId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Cart not found with Id : " + cartId));

        Product product = productRepo.findById(request.getProductId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Product not found with Id : "
                                + request.getProductId()));

        cart.setQuantity(request.getQuantity());
        cart.setProduct(product);

        Cart updatedCart = cartRepo.save(cart);

        CartResponse response = new CartResponse();

        response.setId(updatedCart.getId());
        response.setCustomerId(updatedCart.getCustomer().getId());
        response.setProductId(updatedCart.getProduct().getId());
        response.setProductName(updatedCart.getProduct().getProductName());
        response.setPrice(updatedCart.getProduct().getPrice());
        response.setQuantity(updatedCart.getQuantity());

        response.setTotalPrice(
                updatedCart.getProduct().getPrice()
                        .multiply(BigDecimal.valueOf(updatedCart.getQuantity()))
        );

        return response;
    }

    @Override
    public void removeFromCart(Long cartId) {

        Cart cart = cartRepo.findById(cartId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Cart not found with Id : " + cartId));

        cartRepo.delete(cart);
    }

    @Override
    public void clearCart(Long customerId) {

        Custmer custmer = custmRepo.findById(customerId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Customer not found with Id : "
                                + customerId));

        List<Cart> carts = cartRepo.findByCustomer(custmer);

        cartRepo.deleteAll(carts);
    }
}