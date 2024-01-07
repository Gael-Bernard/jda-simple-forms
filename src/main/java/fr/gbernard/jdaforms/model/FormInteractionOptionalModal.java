package fr.gbernard.jdaforms.model;

import net.dv8tion.jda.api.interactions.modals.Modal;

import java.util.List;
import java.util.Optional;

@FunctionalInterface
public interface FormInteractionOptionalModal {

  Optional<Modal> getOptionalModal(List<String> discordReturnedValues, Form form);

}
