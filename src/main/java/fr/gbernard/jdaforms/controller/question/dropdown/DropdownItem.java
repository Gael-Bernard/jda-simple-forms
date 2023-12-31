package fr.gbernard.jdaforms.controller.question.dropdown;

import net.dv8tion.jda.api.entities.emoji.Emoji;

public interface DropdownItem {

    String getLabel();
    Emoji getEmoji();
    String getValue();

}
