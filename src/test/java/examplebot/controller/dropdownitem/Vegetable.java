package examplebot.controller.dropdownitem;

import fr.gbernard.jdaforms.controller.question.dropdown.DropdownItem;
import net.dv8tion.jda.api.entities.emoji.Emoji;

public enum Vegetable implements DropdownItem {
    POTATO,
    CARROT,
    LETTUCE,
    ROOTS,
    CABBAGE,
    PEAS,
    BEANS;


    @Override
    public String getLabel() {
        return this.name();
    }

    @Override
    public Emoji getEmoji() {
        return Emoji.fromUnicode("➡️");
    }

    @Override
    public String getValue() {
        return this.name().toLowerCase();
    }

    public static Vegetable parse(String value) {
        return Vegetable.valueOf(value.toUpperCase());
    }

}
