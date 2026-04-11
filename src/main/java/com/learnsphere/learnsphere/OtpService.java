	package com.learnsphere.learnsphere;
	
	import java.util.HashMap;
	import java.util.Map;
	import java.util.Random;
	
	import org.springframework.stereotype.Service;
	import org.springframework.web.client.RestTemplate;
	
	@Service
	public class OtpService {
	
	    private Map<String, String> otpStorage = new HashMap<>();
	
	    // 🔥 ADD YOUR AUTH KEY HERE
	    private static final String AUTH_KEY = "C8RrTSpey2KIU7dkbFY4hQHxqjav3mOgPsMiN1u95L0tDoEzBZPjtyhQWJFoiSK0mu7gGdETUarXLZxV";
	
	    public String generateOtp(String mobile) {
	
	        Random random = new Random();
	        int otp = 100000 + random.nextInt(900000);
	
	        String otpString = String.valueOf(otp);
	
	        // ✅ Store OTP (your existing logic)
	        otpStorage.put(mobile, otpString);
	
	        // 🔥 SEND SMS (NEW)
	        sendOtpViaFast2Sms(mobile, otpString);
	
	        return otpString;
	    }
	
	    private void sendOtpViaFast2Sms(String mobile, String otp) {
	        try {

	            String message = java.net.URLEncoder.encode(
	                    "Your OTP is " + otp,
	                    "UTF-8"
	            );

	            String url = "https://www.fast2sms.com/dev/bulkV2"
	                    + "?authorization=" + AUTH_KEY
	                    + "&route=q"
	                    + "&numbers=" + mobile
	                    + "&message=" + message;

	            RestTemplate restTemplate = new RestTemplate();
	            String response = restTemplate.getForObject(url, String.class);

	            System.out.println("Fast2SMS Response: " + response);

	        } catch (Exception e) {
	            System.out.println("Fast2SMS Error: " + e.getMessage());
	        }
	    }
	
	    public boolean verifyOtp(String mobile, String otp) {
	
	        String storedOtp = otpStorage.get(mobile);
	
	        if (storedOtp != null && storedOtp.equals(otp)) {
	            otpStorage.remove(mobile);
	            return true;
	        }
	
	        return false;
	    }
	}