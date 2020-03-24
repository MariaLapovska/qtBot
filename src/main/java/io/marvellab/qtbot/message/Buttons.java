package io.marvellab.qtbot.message;

import com.github.rodionmoiseev.c10n.annotations.En;
import com.github.rodionmoiseev.c10n.annotations.Ru;

public interface Buttons {

    @En("Happy \uD83D\uDE0A")
    @Ru("\u0412\u0435\u0441\u0435\u043B\u043E\u0435 \uD83D\uDE0A")
    String happy();

    @En("Sad \uD83D\uDE14")
    @Ru("\u0413\u0440\u0443\u0441\u0442\u043D\u043E\u0435 \uD83D\uDE14")
    String sad();
}
