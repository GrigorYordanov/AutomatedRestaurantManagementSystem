package utilities;

/**
 * This Enum is used to format the Job Roles.
 * 
 * @author Darren
 *
 */
public enum StaffRoles {
  WAITER, COOK, CHEF, MANAGER;

  /**
   * This method will return the Job Id of particular roles.
   * 
   * @return int Job Id of role.
   */
  public int getId() {
    switch (this) {
      case WAITER:
        return 1;
      case COOK:
        return 2;
      case CHEF:
        return 3;
      case MANAGER:
        return 4;
      default:
        break;
    }
    return 0;
  }
}
