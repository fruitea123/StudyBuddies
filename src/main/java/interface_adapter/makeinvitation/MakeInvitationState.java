package interface_adapter.makeinvitation;

/**
 * a message holder for the view to update.
 * store the current result of make invitation action.
 */
public class MakeInvitationState {
  private String successMessage = "";
  private String errorMessage = "";

  /**
   * method to ensure the string is not null.
   *
   * @param s string to be checked
   * @return string
   */
  private static String nonNull(String s) {
    return s == null ? "" : s;
  }

  /**
   * getter for success message.
   *
   * @return success message
   */
  public String getSuccessMessage() {
    return successMessage;
  }

  /**
   * setter for success message.
   *
   * @param successMessage success message
   */
  public void setSuccessMessage(String successMessage) {
    this.successMessage = nonNull(successMessage);
  }

  /**
   * getter for failure message.
   *
   * @return error message.
   */
  public String getErrorMessage() {
    return errorMessage;
  }

  /**
   * setter for failure message.
   *
   * @param errorMessage error message
   */
  public void setErrorMessage(String errorMessage) {
    this.errorMessage = nonNull(errorMessage);
  }
}