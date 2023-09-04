package com.MobileSubscribers.MobileSubscribers.MobileSubscribers;


import java.util.List;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping(path = "/Subscribers")
@Slf4j
public class SubscriberController {

    private final SubscriberService subscriberService;



    @GetMapping("/mobileSubscriber")
    public String listSubscribers(Model model) {

        model.addAttribute("subscriber", new Subscribers());

        model.addAttribute("subscribers", subscriberService.getAllSubscribers());
        return "mobileSubscriber"; // Assuming you have a "mobileSubscriber.html" template
    }

 @GetMapping("/mobileSubscribers")
    @ResponseBody // This annotation tells Spring to directly return the data as the response body
    public List<Subscribers> mobileSubscribers(Model model) {
     model.addAttribute("subscriber", new Subscribers());

     return subscriberService.getAllSubscribers();
    }


    @GetMapping("/{id}")
    @ResponseBody
    public Subscribers getSubscriber(@PathVariable("id") Long id, Model model){
        model.addAttribute("subscriber", new Subscribers());

        return subscriberService.getSubscriberById(id);
    }

    @GetMapping("/oneMobileSubscriber")
    @ResponseBody
    public ResponseEntity<Subscribers> getSubscriberByMsisdn(@RequestParam String msisdn) {
        Subscribers subscriber = subscriberService.getSubscriberByMsisdn(msisdn);

        if (subscriber != null) {
            return ResponseEntity.ok(subscriber);
        } else {
            return  ResponseEntity.notFound().build();
        }
    }

//

    @GetMapping("/displayImage")
    public String displayImage(Model model) {
        String imageUrl = "/images/super_tech_logo.jpg"; // Path to the image in the static directory
        model.addAttribute("imageUrl", imageUrl);
        return "mobileSubscriber";
    }

    // Other endpoint mappings and methods...

    @PostMapping("/addNewSubscriber")
    public ResponseEntity<String> addNewSubscriber(@RequestBody Subscribers newSubscriber) {
        // Check if a subscriber with the same MSISDN already exists
        if (subscriberService.isMSISDNAlreadyExists(newSubscriber.getMsisdn())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("MSISDN already exists");
        }

        // If no duplicate, proceed with adding the new subscriber
        subscriberService.addNewSubscriber(newSubscriber);
        return ResponseEntity.ok("Subscriber added successfully");
    }


    @GetMapping("/updateSubscriberForm/{id}")
    public String showUpdateSubscriberForm(@PathVariable("id") Long id, Model model) {


        model.addAttribute("subscriber", subscriberService.getSubscriberId(id));


        return "mobileSubscriber";
    }




    @PutMapping("/update/{id}")
    public String updateSubscriber(@PathVariable("id")Long id, @RequestBody Subscribers subscriber, Model model) {

        model.addAttribute("subscriber", subscriber);


            Subscribers existingSubscriber = subscriberService.findById(id);
            existingSubscriber.setMsisdn(subscriber.getMsisdn());
            existingSubscriber.setCustomer_id_user(subscriber.getCustomer_id_user());
            existingSubscriber.setCustomer_id_owner(subscriber.getCustomer_id_owner());
            existingSubscriber.setServiceType(subscriber.getServiceType());

            subscriberService.updateSubscriber(existingSubscriber);
            return "mobileSubscriber";
        }


    @GetMapping("/detailsModal/{id}")
    public String showDetailModal(@PathVariable("id") Long id, Model model) {


        model.addAttribute("subscriberDetails", subscriberService.getSubscriberId(id));


        return "mobileSubscriber";
    }




        //Delete
        @DeleteMapping("/{id}")
        public ResponseEntity<String> deleteSubscribers (@PathVariable Long id){
            try {
                subscriberService.deleteSubscriber(id);
                return ResponseEntity.ok().build();
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
            }
        }

    @GetMapping("/stats")
    public ResponseEntity<SubscriberCount> getStats() {
        long count = subscriberService.countSubscribers();
        long countPrepaid = subscriberService.countSubscribersPrepaid();
        long countPostpaid = subscriberService.countSubscribersPostpaid();

        SubscriberCount subscriberCount = SubscriberCount.builder()
                .totalCount(count)
                .totalPrepaidCount(countPrepaid)
                .totalPostpaidCount(countPostpaid)
                .build();

        // Return the subscriberCount as a JSON response
        return ResponseEntity.ok(subscriberCount);
    }



        @DeleteMapping("/deleteAll")
        public void deleteAllSubscribers () {
            subscriberService.deleteAllSubscribers();
        }


}
