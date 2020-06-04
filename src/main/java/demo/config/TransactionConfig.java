package demo.config;

import java.util.Collections;
import java.util.List;
import java.util.Properties;

import org.aspectj.lang.annotation.Aspect;
import org.springframework.aop.Advisor;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.interceptor.RollbackRuleAttribute;
import org.springframework.transaction.interceptor.RuleBasedTransactionAttribute;
import org.springframework.transaction.interceptor.TransactionInterceptor;

@Aspect
@Configuration
public class TransactionConfig {
    @Autowired
    private PlatformTransactionManager transactionManager;
    
    private static final String EXPRESSION = "execution(* demo..*Impl.*(..))";

    private static final int TX_METHOD_TIMEOUT = 60;

    @Bean
	public TransactionInterceptor txAdvice() {
        TransactionInterceptor txAdvice = new TransactionInterceptor();
        Properties txAttributes = new Properties();

		List<RollbackRuleAttribute> rollbackRules = Collections.singletonList(new RollbackRuleAttribute(Exception.class));

		RuleBasedTransactionAttribute writeAttribute = new RuleBasedTransactionAttribute(TransactionDefinition.PROPAGATION_REQUIRED, rollbackRules);
        writeAttribute.setTimeout(TX_METHOD_TIMEOUT);

        RuleBasedTransactionAttribute newAttribute = new RuleBasedTransactionAttribute(TransactionDefinition.PROPAGATION_REQUIRES_NEW, rollbackRules);
        newAttribute.setTimeout(TX_METHOD_TIMEOUT);

        String writeTransactionAttributesDefinition = writeAttribute.toString();
        String newTransactionAttributesDefinition = newAttribute.toString();

        // write rollback-rule
        txAttributes.setProperty("insert*", writeTransactionAttributesDefinition);
        txAttributes.setProperty("update*", writeTransactionAttributesDefinition);
        txAttributes.setProperty("delete*", writeTransactionAttributesDefinition);
        
        // new rollback-rule
        txAttributes.setProperty("new*", newTransactionAttributesDefinition);
        
        txAdvice.setTransactionAttributes(txAttributes);
        txAdvice.setTransactionManager(transactionManager);

		return txAdvice;
	}

	@Bean
	public Advisor txAdviceAdvisor() {
		AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
		pointcut.setExpression(EXPRESSION);

		return new DefaultPointcutAdvisor(pointcut, txAdvice());
	}


}