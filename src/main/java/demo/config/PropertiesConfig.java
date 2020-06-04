package demo.config;

import java.util.Map;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

@Component
@EnableConfigurationProperties
@ConfigurationProperties
public class PropertiesConfig {

    @Setter
    @Getter
    public Map<String, Map<String, String>> common ;
 
    public String getData(String fir, String sec){
        String rtnData = "";
        Map<String, Map<String, String>> data = this.getCommon();

        if((data != null && !data.isEmpty()) && (data.get(fir) != null && !data.get(fir).isEmpty())){
            rtnData = (data.get(fir).get(sec) != null && !data.get(fir).get(sec).isEmpty()) ? data.get(fir).get(sec) : "" ;
        }
      
        return rtnData;
    }
}