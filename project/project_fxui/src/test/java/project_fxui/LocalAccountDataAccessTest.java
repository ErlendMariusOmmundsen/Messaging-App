
package project_fxui;

public class LocalAccountDataAccessTest extends AccountDataAccessTest {

  @Override
  protected AccountDataAccess getDataAccess() {
    return new LocalAccountDataAccess();
  }

}
