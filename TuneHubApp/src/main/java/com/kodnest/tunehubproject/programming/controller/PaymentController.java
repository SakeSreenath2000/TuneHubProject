package com.kodnest.tunehubproject.programming.controller;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.kodnest.tunehubproject.programming.entities.Users;
import com.kodnest.tunehubproject.programming.services.UsersService;
import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import com.razorpay.Utils;

import jakarta.servlet.http.HttpSession;

@Controller
public class PaymentController {
	
	@Autowired
	UsersService service;
	
	@PostMapping("/createOrder")
	@ResponseBody
	public String createOrder() {
		/*We kept Order inside try block but it will become Local Variable for try block , and we get error while returning the order
		 												so initially we created an Order assigning null to it.*/
		Order order = null;
		//API Sample Code might throws some exceptions so we kept it inside the try and catch block to handle them.
		try {
			//here we used API Sample Code of RazorPay to Create an Order
			/*While we are integrating/adding  the payment gateway to our java code (or) website then it will send 
			 																							the request in form of order to RazorPay to initiate the order/payment*/
					RazorpayClient razorpay = new RazorpayClient("rzp_test_TBcMw8qXaYHDNm", "2Aoj0220oE4ekEat9Qf9Gt7Q");
					JSONObject orderRequest = new JSONObject();
					orderRequest.put("amount", 50000);
					orderRequest.put("currency", "INR");
					orderRequest.put("receipt", "receipt#1");
					JSONObject notes = new JSONObject();
					notes.put("notes_key_1", "Tea, Earl Grey, Hot");
					orderRequest.put("notes", notes);
					order = razorpay.orders.create(orderRequest);
			} 
			catch (Exception e) {
				System.out.println("Exception While Creating the Order");
			}
		//here we are not only returning the normal text or html page rather we are returning the order
		return order.toString();
	}

	@PostMapping("/verify")
	@ResponseBody
	public boolean verifyPayment(@RequestParam String orderId, @RequestParam String paymentId,@RequestParam String signature) {
		//here we are using the the Verify Payment Signature code of RazorPay to verify the payment is success or not
		try {
					// Initialize Razorpay client with your API key and secret
					RazorpayClient razorpayClient = new RazorpayClient("rzp_test_TBcMw8qXaYHDNm", "2Aoj0220oE4ekEat9Qf9Gt7Q");
					// Create a signature verification data string
					String verificationData = orderId + "|" + paymentId;

					// Use Razorpay's utility function to verify the signature
					boolean isValidSignature = Utils.verifySignature(verificationData, signature, "2Aoj0220oE4ekEat9Qf9Gt7Q");
					
					//here the response will be sent to "/verify" url which is present in samplePayment.html page
					return isValidSignature;
				} 
				catch (RazorpayException e) {
						e.printStackTrace();
						return false;	
				}
		
	}
	//payment-success -> update to premium user
	@GetMapping("payment-success")
	public String paymentSuccess (HttpSession session) {
		//session objects are not created by us they are automatically created by the SpringBoot.
		/*sessions are used to track that which user has logged in and track the entire work done by the user until he logout , 
				 																																		if he logout the session will be destroyed automatically.*/
		//session.getAttribute() returns object class in order to take the email we downcast it in to String type
		String email=(String) session.getAttribute("email");
		/*i.e we are getting the email from the session and using getUser() from the UsersService to know whether the user is 
																																																					paid customer or not*/
		Users user=service.getUser(email);
		//here we are setting the premium as true i.e means the customer is paid 
		user.setPremium(true);
		//here we are updating the user as he is now the paid user and then he will be redirected to login page
		service.updateUser(user);
		return "login";
	}
	//if the payment is failed then he will be redirected to login page again .
	//payment-failure -> redirect to login
	@GetMapping("payment-failure")
	public String paymentFailure() {
		return  "payment";
		//return "login";
	}

}
