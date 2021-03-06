package cl.transbank.onepay.example.controller;

import cl.transbank.onepay.Onepay;
import cl.transbank.onepay.example.ComerceConfig;
import cl.transbank.onepay.model.Options;
import cl.transbank.onepay.model.Refund;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class RefundController {
    @RequestMapping(value = "/refund", method = RequestMethod.GET)
    public ModelAndView refundCreate(@RequestParam("amount") long amount,
                                     @RequestParam("occ") String occ,
                                     @RequestParam("externalUniqueNumber") String externalUniqueNumber,
                                     @RequestParam("authorizationCode") String authorizationCode) {
        Onepay.setIntegrationType(Onepay.IntegrationType.TEST);

        // create options to send Onepay's keys
        Options options = Options.getDefaults()
                .setApiKey(ComerceConfig.ONEPAY_API_KEY)
                .setSharedSecret(ComerceConfig.ONEPAY_SHARED_SECRET);
        try {
            Refund.create(amount, occ, externalUniqueNumber, authorizationCode, options);
            return new ModelAndView("refund-success");
        } catch (Throwable e) {
            e.printStackTrace();
            return new ModelAndView("service-error", "message", e.getMessage());
        }
    }
}
