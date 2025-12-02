package interface_adapter.makeinvitation;

import java.time.LocalDate;
import java.time.LocalTime;
import use_case.makeinvitation.MakeInvitationInputBoundary;
import use_case.makeinvitation.MakeInvitationInputData;

/**
 * Controller for the make invitation use case.
 * Receives data from the view and calls the interactor.
 */
public class MakeInvitationController {
  private final MakeInvitationInputBoundary interactor;

  /**
   * controller for packing the input invitation data.
   *
   * @param interactor input boundary to create input data
   */
  public MakeInvitationController(MakeInvitationInputBoundary interactor) {
    this.interactor = interactor;
  }

  /**
   * Handles the create invitation button click.
   *
   * @param course      input course
   * @param description input description
   * @param date        input date
   * @param start       input start time
   * @param end         input end time
   * @param mode        input mode
   * @param location    input location
   * @param occupancy   input occupancy
   */
  public void onConfirm(String course,
                        String description,
                        LocalDate date,
                        LocalTime start,
                        LocalTime end,
                        String mode,
                        String location,
                        Integer occupancy) {
    MakeInvitationInputData req = new MakeInvitationInputData(
        course, description, date, start, end, mode, location, occupancy);

    interactor.execute(req);
  }
}

