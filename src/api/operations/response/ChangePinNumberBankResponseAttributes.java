package api.operations.response;

import api.operations.ChangePinNumber;

public class ChangePinNumberBankResponseAttributes extends BankResponseAttributes<ChangePinNumber> {
    private ChangePinFailReason changePinFailReason;

    public ChangePinNumberBankResponseAttributes(boolean isSuccessful, ChangePinFailReason changePinFailReason) {
        super(isSuccessful);
        this.changePinFailReason = changePinFailReason;
    }

    public ChangePinFailReason getChangePinFailReason() {
        return changePinFailReason;
    }

    public enum ChangePinFailReason {
        NoSuchCard("No card with given number "),
        SamePinNumber("New PIN number was not changed: ");

        private String text;

        ChangePinFailReason(String text) {
            this.text = text;
        }
    }
}
