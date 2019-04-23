package RavintolaUI;

import dao.UsersInFile;
import domain.Chef;
import domain.Manager;
import domain.User;
import domain.Waiter;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class RavintolaUI {

    private List<User> users;
    private UsersInFile userReader;

    public RavintolaUI(UsersInFile userReader) throws IOException {
        this.userReader = userReader;
        userReader.readUsersFromFile();
    }

    public void login() throws Exception {

        Scanner s = new Scanner(System.in);

        leiska.viiva();
        System.out.print("Username: ");

        String username = s.nextLine();

        System.out.print("Password: ");

        String password = s.nextLine();

        leiska.viiva();
        User user = null;

        user = this.userReader.getUser(username);

        if (!user.getPassword().equals(password) || user == null) {
            System.out.println("Wrong username or password");
            return;
        }

        welcome(user);

    }

    public void welcome(User user) throws Exception {
        Scanner s = new Scanner(System.in);
        while (true) {
            System.out.println("Welcome " + user.getUsername());
            leiska.viiva();
            System.out.println("0 = log out and save");
            System.out.println("1 = change password");
            System.out.println("2 = continue");
            String valinta = leiska.kysy();

            if (valinta.equals("0")) {
                user.saveAll();
                return;
            } else if (valinta.equals("1")) {
                System.out.println("New password: ");
                String newPassword = leiska.kysy();
                user.changePassword(newPassword);
                continue;
            }

            if (user.getStatus().equals("waiter")) {
                welcomeWaiter((Waiter) user);
            }

            if (user.getStatus().equals("manager")) {
                welcomeManager((Manager) user);
            }

            if (user.getStatus().equals("chef")) {
                welcomeChef((Chef) user);
            }
        }

    }

    public static void welcomeWaiter(Waiter waiter) throws Exception {
        while (true) {
            System.out.println("0 = to start");
            System.out.println("1 = manage tables");
            String valinta = leiska.kysy();

            if (valinta.equals("0")) {
                break;
            } else if (valinta.equals("1")) {
                WaiterUI ui = new WaiterUI(waiter);
                ui.manageTables();
            }

        }
    }

    public void welcomeManager(Manager manager) throws IOException {
        while (true) {

            System.out.println("0 = to start");
            System.out.println("1 = manage menu");
            System.out.println("2 = add or remove waiters");
            String valinta = leiska.kysy();
            manager.setUserReader(userReader);
            ManagerUI ui = new ManagerUI(manager);

            if (valinta.equals("0")) {
                break;
            }
            if (valinta.equals("1")) {
                ui.manageMenu();
            } else if (valinta.equals("2")) {
                ui.manageWaiters();
            }

        }
    }

    public static void welcomeChef(Chef chef) {
        while (true) {
            System.out.println("0 = to start");
            System.out.println("1 = print inventory");
            String valinta = leiska.kysy();

            if (valinta.equals("0")) {
                break;
            }
            if (valinta.equals("1")) {
                chef.printInventory();
            }
        }
    }

}
