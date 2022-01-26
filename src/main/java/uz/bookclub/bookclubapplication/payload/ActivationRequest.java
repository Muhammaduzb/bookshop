package uz.bookclub.bookclubapplication.payload;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

/**
 * created by Muhammad
 * on 21.07.2020
 */

public class ActivationRequest {
    @NotEmpty(message = "Iltimos sizga yuborilgan kodni kiriting!")
    @Pattern(regexp = "[0-9]{4}", message = "Aktiv kod 4 xonali son!")
    private String activeCode;

    public String getActiveCode() {
        return activeCode;
    }

    public void setActiveCode(String activeCode) {
        this.activeCode = activeCode;
    }
}
