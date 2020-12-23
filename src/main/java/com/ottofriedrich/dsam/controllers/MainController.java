package com.ottofriedrich.dsam.controllers;

import com.ottofriedrich.dsam.dtos.*;
import com.ottofriedrich.dsam.models.*;
import com.ottofriedrich.dsam.repositories.*;
import com.ottofriedrich.dsam.services.BottleService;
import com.ottofriedrich.dsam.services.CrateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Controller
public class MainController {

    @Autowired
    private BottleService bottleService;

    @Autowired
    private CrateService crateService;

    @Autowired
    private BottleRepository bottleRepository;

    @Autowired
    private CrateRepository crateRepository;

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/home")
    public String viewHomePage(Model model) {
        MainDTO mainDTO = new MainDTO();
        mainDTO.setBottleDTOS(bottleService.getAllBottles());
        mainDTO.setCrateDTOS(crateService.getAllCrates());
        model.addAttribute("response", mainDTO);
        return "home";
    }

    @GetMapping("/add-bottle-to-cart")
    public String addBottlesToCart(@RequestParam("id") Long id, HttpSession httpSession) {
        CartDTO cartDTO = null;
        Bottle bottle = bottleRepository.getOne(id);
        boolean bottleAvailable = false;

        if (bottle != null && bottle.getInStock() > 0) {
            if (httpSession.getAttribute("cart") != null) {
                cartDTO = (CartDTO) httpSession.getAttribute("cart");
                if (cartDTO.getBottleDTOS() != null) {
                    List<BottleDTO> bottleDTOS = cartDTO.getBottleDTOS();
                    int i = 0;
                    for(BottleDTO bottleDTO : bottleDTOS) {
                        if (bottleDTO.getId().equals(bottle.getId())) {
                            bottleDTO.setCount(bottleDTO.getCount() + 1);
                            bottleDTOS.remove(i);
                            bottleDTOS.add(bottleDTO);
                            cartDTO.setBottleDTOS(bottleDTOS);
                            bottleAvailable = true;
                            break;
                        }
                        i++;
                    }
                }

                if (!bottleAvailable) {
                    List<BottleDTO> bottleDTOS = cartDTO.getBottleDTOS();
                    if (bottleDTOS == null) {
                        bottleDTOS = new ArrayList<>();
                    }
                    BottleDTO bottleDTO = BottleDTO.valueOf(bottle);
                    bottleDTO.setCount(1);
                    bottleDTOS.add(bottleDTO);
                    cartDTO.setBottleDTOS(bottleDTOS);
                }
                httpSession.setAttribute("cart", cartDTO);
            } else {
                CartDTO cartDTO1 = new CartDTO();
                List<BottleDTO> bottleDTOS = new ArrayList<>();
                BottleDTO bottleDTO = BottleDTO.valueOf(bottle);
                bottleDTO.setCount(1);
                bottleDTOS.add(bottleDTO);
                cartDTO1.setBottleDTOS(bottleDTOS);
                httpSession.setAttribute("cart", cartDTO1);
            }
            bottle.setInStock(bottle.getInStock() - 1);
            bottleRepository.save(bottle);
        }
        return "redirect:/home";
    }

    @GetMapping("/add-crate-to-cart")
    public String addCratesToCart(@RequestParam("id") Long id, HttpSession httpSession) {
        CartDTO cartDTO = null;
        Crate crate = crateRepository.getOne(id);
        boolean bottleAvailable = false;

        if (crate != null && crate.getCratesInStock() > 0) {
            if (httpSession.getAttribute("cart") != null) {
                cartDTO = (CartDTO) httpSession.getAttribute("cart");
                if (cartDTO.getCrateDTOS() != null) {
                    int i = 0;
                    List<CrateDTO> crateDTOS = cartDTO.getCrateDTOS();
                    for(CrateDTO crateDTO : crateDTOS) {
                        if (crateDTO.getId().equals(crate.getId())) {
                            crateDTO.setCount(crateDTO.getCount() + 1);
                            crateDTOS.remove(i);
                            crateDTOS.add(crateDTO);
                            cartDTO.setCrateDTOS(crateDTOS);
                            bottleAvailable = true;
                            break;
                        }
                        i++;
                    }
                }

                if (!bottleAvailable) {
                    List<CrateDTO> crateDTOS = cartDTO.getCrateDTOS();
                    if (crateDTOS == null) {
                        crateDTOS = new ArrayList<>();
                    }
                    CrateDTO crateDTO = new CrateDTO();
                    crateDTO = CrateDTO.valueOf(crate);
                    crateDTO.setCount(1);
                    crateDTOS.add(crateDTO);
                    cartDTO.setCrateDTOS(crateDTOS);
                }
                httpSession.setAttribute("cart", cartDTO);
            } else {
                CartDTO cartDTO1 = new CartDTO();
                List<CrateDTO> crateDTOS = new ArrayList<>();
                CrateDTO crateDTO = CrateDTO.valueOf(crate);
                crateDTO.setCount(1);
                crateDTOS.add(crateDTO);
                cartDTO1.setCrateDTOS(crateDTOS);
                httpSession.setAttribute("cart", cartDTO1);
            }
            crate.setCratesInStock(crate.getCratesInStock() - 1);
            crateRepository.save(crate);
        }
        return "redirect:/home";
    }

    @GetMapping("/cart")
    public String viewCartPage(Model model, HttpSession httpSession) {
        double totalPrice = 0.0;
        CartDTO cartDTO = (CartDTO) httpSession.getAttribute("cart");
        if (cartDTO != null && cartDTO.getBottleDTOS() != null) {
            for (BottleDTO bottleDTO : cartDTO.getBottleDTOS()) {
                totalPrice += (bottleDTO.getPrice() * bottleDTO.getCount());
            }
        }

        if (cartDTO != null && cartDTO.getCrateDTOS() != null) {
            for (CrateDTO crateDTO : cartDTO.getCrateDTOS()) {
                totalPrice += (crateDTO.getPrice() * crateDTO.getCount());
            }
        }

        httpSession.setAttribute("total", totalPrice);

        return "cart";
    }

    @PostMapping("/place-order")
    public String placeOrder(AddressDTO addressDTO, HttpSession httpSession) {
        Address address = Address.valueOf(addressDTO);
        User user = (User) httpSession.getAttribute("user");
        address.setUser(user);
        addressRepository.save(address);
        OnlineOrder onlineOrder = new OnlineOrder();
        double total = (double) httpSession.getAttribute("total");
        onlineOrder.setPrice(total);
        onlineOrder.setUser(user);
        onlineOrder = orderRepository.save(onlineOrder);
        CartDTO cartDTO = (CartDTO) httpSession.getAttribute("cart");
        if (cartDTO.getBottleDTOS() != null) {
            for (BottleDTO bottleDTO : cartDTO.getBottleDTOS()) {
                OrderItem orderItem = new OrderItem();
                Bottle bottle = bottleRepository.getOne(bottleDTO.getId());
                orderItem.setBottle(bottle);
                orderItem.setOrder(onlineOrder);
                orderItem.setPosition("1");
                orderItem.setPrice(bottleDTO.getPrice() * bottleDTO.getCount());
                orderItem.setQuantity(bottleDTO.getCount());
                orderItemRepository.save(orderItem);
            }
        }

        if (cartDTO.getCrateDTOS() != null) {
            for (CrateDTO crateDTO : cartDTO.getCrateDTOS()) {
                OrderItem orderItem = new OrderItem();
                Crate crate = crateRepository.getOne(crateDTO.getId());
                orderItem.setCrate(crate);
                orderItem.setOrder(onlineOrder);
                orderItem.setPosition("1");
                orderItem.setPrice(crateDTO.getPrice() * crateDTO.getCount());
                orderItem.setQuantity(crateDTO.getCount());
                orderItemRepository.save(orderItem);
            }
        }
        httpSession.removeAttribute("cart");
        return "redirect:/orders";
    }

    @GetMapping("/orders")
    public String viewOrders(Model model, HttpSession httpSession) {
        User user = (User) httpSession.getAttribute("user");
        List<OnlineOrder> onlineOrders = orderRepository.findByUserId(user.getId());
        MainDTO mainDTO = new MainDTO();
        List<OrderDTO> orderDTOS = new ArrayList<>();
        for (OnlineOrder onlineOrder : onlineOrders) {
            List<OrderItem> orderItems = orderItemRepository.findByOrderId(onlineOrder.getId());
            OrderDTO orderDTO = new OrderDTO();
            orderDTO.setId(onlineOrder.getId());
            orderDTO.setTotal(onlineOrder.getPrice());
            List<OrderItemDTO> orderItemDTOS = new ArrayList<>();
            orderItems.forEach(orderItem -> {
                OrderItemDTO orderItemDTO = new OrderItemDTO();
                if (orderItem.getBottle() != null) {
                    orderItemDTO.setName(orderItem.getBottle().getName());
                    orderItemDTO.setImage(orderItem.getBottle().getBottlePic());
                    orderItemDTO.setPrice(orderItem.getBottle().getPrice());
                    orderItemDTO.setQuantity(orderItem.getQuantity());
                }
                if (orderItem.getCrate() != null) {
                    orderItemDTO.setName(orderItem.getCrate().getName());
                    orderItemDTO.setImage(orderItem.getCrate().getCratePic());
                    orderItemDTO.setPrice(orderItem.getCrate().getPrice());
                    orderItemDTO.setQuantity(orderItem.getQuantity());
                }
                orderItemDTOS.add(orderItemDTO);
            });
            orderDTO.setOrderItemDTOS(orderItemDTOS);
            orderDTOS.add(orderDTO);
        }
        mainDTO.setOrderDTOS(orderDTOS);
        model.addAttribute("response", mainDTO);
        return "orders";
    }

    @GetMapping("/login")
    public String viewLogin(Model model) {
        return "login";
    }

    @GetMapping("/register")
    public String viewRegister(Model model) {
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(User user, RedirectAttributes redirectAttributes) {
        User user1 = userRepository.findByUsername(user.getUsername());
        if (user1 != null) {
            redirectAttributes.addFlashAttribute("message", "Username already exists");
            return "redirect:/register";
        }
        userRepository.save(user);
        redirectAttributes.addFlashAttribute("message", "User registered successfully");
        return "redirect:/login";
    }
}
