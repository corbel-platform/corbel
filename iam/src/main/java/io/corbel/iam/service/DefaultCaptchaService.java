package io.corbel.iam.service;

import ch.compile.recaptcha.ReCaptchaVerify;
import ch.compile.recaptcha.model.SiteVerifyResponse;
import io.corbel.iam.api.IamErrorResponseFactory;

import javax.ws.rs.NotFoundException;
import java.io.IOException;
import java.util.Map;
import java.util.Optional;

/**
 * @author Alberto J. Rubio
 */
public class DefaultCaptchaService implements CaptchaService {

    private static final String CAPTCHA_SECRET_KEY = "secret_key";
    private static final String CAPTCHA_KEY = "captcha";

    private final DomainService domainService;

    public DefaultCaptchaService(DomainService domainService) {
        this.domainService = domainService;
    }

    @Override
    public boolean verifyRequestCaptcha(String domainId, String captcha) {
        return domainService.getDomain(domainId).map(domain -> {
            Map<String, String> configuration = domain.getAuthConfigurations().get(CAPTCHA_KEY);
            if(configuration != null) {
                ReCaptchaVerify reCaptchaVerify = new ReCaptchaVerify(configuration.get(CAPTCHA_SECRET_KEY));
                try {
                    SiteVerifyResponse siteVerifyResponse = reCaptchaVerify.verify(captcha, null);
                    return siteVerifyResponse.isSuccess();
                } catch (IOException e) {
                    return false;
                }
            } else return true;
        }).orElse(false);
    }
}
