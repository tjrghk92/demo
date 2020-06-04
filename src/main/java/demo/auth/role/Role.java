package demo.auth.role;

import lombok.Getter;

@Getter
public enum Role {
    ROLE_NORMAL("10"),
    ROLE_SPECIAL("11");

    Role(String value){
        this.value = value;
    }
    
    private String value;
    




}