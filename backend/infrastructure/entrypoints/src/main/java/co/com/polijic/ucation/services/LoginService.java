package co.com.polijic.ucation.services;

import co.com.polijic.ucation.domain.common.LoginModel;
import co.com.polijic.ucation.domain.requesters.LoginRequester;
import co.com.polijic.ucation.usecases.ports.LoginPort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class LoginService {

    private final LoginPort loginPort;

    public LoginService(LoginPort loginPort) {
        this.loginPort = loginPort;
    }

    @Transactional(readOnly = true, rollbackFor = Exception.class)
    public LoginModel login(LoginRequester requester) throws Exception {
        return loginPort.login(requester);
    }

    @Transactional(rollbackFor = Exception.class)
    public void logout(String token) {
        loginPort.logout(token);
    }
}
