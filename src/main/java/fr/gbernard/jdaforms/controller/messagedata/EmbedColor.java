package fr.gbernard.jdaforms.controller.messagedata;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum EmbedColor {
    SUCCESS(MessageGlobalParams.SUCCESS_COLOR),
    ERROR(MessageGlobalParams.ERROR_COLOR),
    WARNING(MessageGlobalParams.WARNING_COLOR),
    NEUTRAL(MessageGlobalParams.NEUTRAL_COLOR);

    private final int code;
}
