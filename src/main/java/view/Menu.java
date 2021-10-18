package view;

import org.apache.commons.lang3.StringUtils;

import static java.lang.System.out;

public class Menu {
    private String title;
    private String[] options;

    public Menu(String title, String[] options) {
        this.title = title;
        this.options = new String[options.length];

        for (int i = 0; i < options.length; i++) {
            this.options[i] = options[i];
        }
    }

    public void show() {
        out.print(IO.COLOR_ANSI_GREEN);
        out.println(StringUtils.center("▂▃▄▅▆▇ " + this.title.toUpperCase() + " ▇▆▅▄▃▂", IO.WINDOW_WIDTH));
        out.print(IO.COLOR_ANSI_RESET);

        System.out.println(StringUtils.center(
                StringUtils.rightPad(" ╔", IO.TEXT_MAX_WIDTH, "═") + "╗ ", IO.WINDOW_WIDTH)
        );

        for (int i = 0; i < this.options.length; i++) {
            if (i == 0) {
                System.out.println(StringUtils.center(
                        StringUtils.rightPad("║ " + ((i + 1) % this.options.length) + ". " + this.options[i], IO.TEXT_MAX_WIDTH - 2) + " ║", IO.WINDOW_WIDTH)
                );
            } else {
                System.out.println(StringUtils.center(
                        StringUtils.rightPad("╠", IO.TEXT_MAX_WIDTH - 1, "═") + "╣", IO.WINDOW_WIDTH)
                );
                System.out.println(StringUtils.center(
                        StringUtils.rightPad("║ " + ((i + 1) % this.options.length) + ". " + this.options[i], IO.TEXT_MAX_WIDTH - 2) + " ║", IO.WINDOW_WIDTH)
                );
            }
        }
        System.out.println(StringUtils.center(
                StringUtils.rightPad(" ╚", IO.TEXT_MAX_WIDTH, "═") + "╝ ", IO.WINDOW_WIDTH)
        );
    }

    public void list() {
        System.out.println(StringUtils.center(
                StringUtils.rightPad( IO.COLOR_ANSI_CYAN + title.toUpperCase() + IO.COLOR_ANSI_RESET, IO.TEXT_MAX_WIDTH), IO.WINDOW_WIDTH)
        );

        out.print("\n");

        for(int i = 0; i < options.length; i++) {
            System.out.println(StringUtils.center(
                    StringUtils.rightPad("[" + IO.COLOR_ANSI_BLUE + i + IO.COLOR_ANSI_RESET + "] "+ options[i], IO.TEXT_MAX_WIDTH), IO.WINDOW_WIDTH)
            );
        }

        out.print("\n");
    }
}
