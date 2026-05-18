package co.com.polijic.ucation.usecases.ports;

import co.com.polijic.ucation.domain.common.LoginModel;
import co.com.polijic.ucation.domain.requesters.LoginRequester;

public interface LoginPort {

    LoginModel login(LoginRequester requester) throws Exception;
    void logout(String token);
}
