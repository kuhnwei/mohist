package mohist.examples.spring.springboot.handler;

import org.assertj.core.util.Sets;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.common.util.OAuth2Utils;
import org.springframework.security.oauth2.provider.AuthorizationRequest;
import org.springframework.security.oauth2.provider.OAuth2RequestFactory;
import org.springframework.security.oauth2.provider.approval.Approval;
import org.springframework.security.oauth2.provider.approval.ApprovalStore;
import org.springframework.security.oauth2.provider.approval.ApprovalStoreUserApprovalHandler;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;

import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import java.util.Set;

/**
 * @author Kuhn, email@kuhnwei.com
 * @date 2020/2/26
 **/
public class CustomApprovalHandler extends ApprovalStoreUserApprovalHandler {

    private int approvalExpirySeconds = -1;
    private ApprovalStore approvalStore;

    public CustomApprovalHandler(JdbcClientDetailsService clientDetailsService, ApprovalStore approvalStore, OAuth2RequestFactory oAuth2RequestFactory) {
        this.setClientDetailsService(clientDetailsService);
        this.setApprovalStore(approvalStore);
        this.setRequestFactory(oAuth2RequestFactory);
    }

    @Override
    public AuthorizationRequest updateAfterApproval(AuthorizationRequest authorizationRequest, Authentication userAuthentication) {
        Set<String> requestScopes = authorizationRequest.getScope();
        Set<String> approvedScopes = Sets.newHashSet();
        Set<Approval> approvals = Sets.newHashSet();

        Date expiry = computeExpiry();

        Map<String, String> approvalParams = authorizationRequest.getApprovalParameters();
        requestScopes.forEach(rs -> {
            String approvalParam = OAuth2Utils.SCOPE_PREFIX + rs;
            String value = approvalParams.get(approvalParam);
            value = value == null ? "" : value.toLowerCase();
            if ("true".equals(value) || value.startsWith("approve") || "on".equals(value)) {
                approvedScopes.add(rs);
                approvals.add(new Approval(userAuthentication.getName(), authorizationRequest.getClientId(),
                        rs, expiry, Approval.ApprovalStatus.APPROVED));
            } else {
                approvals.add(new Approval(userAuthentication.getName(), authorizationRequest.getClientId(),
                        rs, expiry, Approval.ApprovalStatus.DENIED));
            }
        });
        approvalStore.addApprovals(approvals);
        boolean approved;
        authorizationRequest.setScope(approvedScopes);
        if (approvedScopes.isEmpty() && !requestScopes.isEmpty()) {
            approved = false;
        }
        else {
            approved = true;
        }
        authorizationRequest.setApproved(approved);
        return authorizationRequest;
    }

    private Date computeExpiry() {
        Calendar expiresAt = Calendar.getInstance();
        // use default of 1 month
        if (approvalExpirySeconds == -1) {
            expiresAt.add(Calendar.MONTH, 1);
        }
        else {
            expiresAt.add(Calendar.SECOND, approvalExpirySeconds);
        }
        return expiresAt.getTime();
    }
}
