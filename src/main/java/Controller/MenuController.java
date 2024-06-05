
package Controller;

import View.MenuView;

public class MenuController {
    private MenuView menuView = new MenuView();

    public void iniciar() {
        menuView.exibirMenu();
    }
}
