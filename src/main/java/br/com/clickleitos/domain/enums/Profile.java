package br.com.clickleitos.domain.enums;

public enum Profile {
    ADMIN_SYSTEM(1 , "ROLE_ADMIN_SYSTEM"),
    ADMIN_UNIDADE(2,"ROLE_ADMIN_UNIDADE"),
    ADMIN_LEITO(3,"ROLE_ADMIN_LEITO"),
    USUARIO(4,"ROLE_USUARIO");

    private int code;
    private String description;

    private Profile(int code, String description){
        this.code = code;
        this.description = description;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public static Profile toEnum(Integer cod) {

        if (cod == null) {
            return null;
        }

        for (Profile x : Profile.values()) {
            if (cod.equals(x.getCode())) {
                return x;
            }
        }

        throw new IllegalArgumentException("Id inválido: " + cod);
    }
}
