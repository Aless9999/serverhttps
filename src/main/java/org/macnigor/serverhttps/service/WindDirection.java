package org.macnigor.serverhttps.service;

import org.springframework.stereotype.Component;

@Component
public class WindDirection {



    public String getWindDirectionRu(int degrees) {
        if (degrees >= 337.5 || degrees < 22.5) return "С";
        if (degrees >= 22.5 && degrees < 67.5) return "СВ";
        if (degrees >= 67.5 && degrees < 112.5) return "В";
        if (degrees >= 112.5 && degrees < 157.5) return "ЮВ";
        if (degrees >= 157.5 && degrees < 202.5) return "Ю";
        if (degrees >= 202.5 && degrees < 247.5) return "ЮЗ";
        if (degrees >= 247.5 && degrees < 292.5) return "З";
        if (degrees >= 292.5 && degrees < 337.5) return "СЗ";
        return "";
    }


    public String getWindDirectionEn(int degrees) {
        if (degrees >= 337.5 || degrees < 22.5) return "N";   // North
        if (degrees >= 22.5 && degrees < 67.5) return "NE";  // North-East
        if (degrees >= 67.5 && degrees < 112.5) return "E";   // East
        if (degrees >= 112.5 && degrees < 157.5) return "SE"; // South-East
        if (degrees >= 157.5 && degrees < 202.5) return "S";   // South
        if (degrees >= 202.5 && degrees < 247.5) return "SW"; // South-West
        if (degrees >= 247.5 && degrees < 292.5) return "W";   // West
        if (degrees >= 292.5 && degrees < 337.5) return "NW"; // North-West
        return "N/A";
    }

    public String getWindDirectionArrow(int degrees) {
        if (degrees >= 337.5 || degrees < 22.5) return "↓";   // North (ветер С СЕВЕРА, дует вниз)
        if (degrees >= 22.5 && degrees < 67.5) return "↙️";  // North-East
        if (degrees >= 67.5 && degrees < 112.5) return "←";   // East
        if (degrees >= 112.5 && degrees < 157.5) return "↖️"; // South-East
        if (degrees >= 157.5 && degrees < 202.5) return "↑️";   // South
        if (degrees >= 202.5 && degrees < 247.5) return "↗️"; // South-West
        if (degrees >= 247.5 && degrees < 292.5) return "→️";   // West
        if (degrees >= 292.5 && degrees < 337.5) return "↘️"; // North-West
        return "·";
    }
}
