package br.com.clickleitos.domain.enums;

public enum Profile {
    ADMIN(1,"ROLE_ADMIN"),
    USUARIO(2,"ROLE_USUARIO"),
    ADMIN_UNIDADE(3, "ROLE_UNIDADE");

    private int code;
    private String description;

    Profile(int code, String description){
        this.code = code;
        this.description = description;
    }

    public int getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }

    public static Profile toEnum(Integer code) {

        if (code == null) {
            return null;
        }

        for (Profile x : Profile.values()) {
            if (code.equals(x.getCode())) {
                return x;
            }
        }

        throw new IllegalArgumentException("Id inválido: " + code);
    }
}
