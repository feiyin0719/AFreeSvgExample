package com.yf.afreesvg.gradient;

import com.yf.afreesvg.PosMode;
import com.yf.afreesvg.SVGBaseTest;


import org.w3c.dom.Element;

import static org.junit.Assert.assertEquals;

public class SVGGradientBaseTest extends SVGBaseTest {

    public void baseGradientTest(SVGBaseGradient gradient) {
        gradient.addStopColor(0.5f, 0xffff0000);
        gradient.addStopColor(0.75f, 0xffff0000);
        assertEquals(SVGBaseGradient.SpreadMode.SPREAD_PAD, gradient.getSpreadMode());
        gradient.setSpreadMode(SVGBaseGradient.SpreadMode.SPREAD_REFLECT);
        assertEquals(SVGBaseGradient.SpreadMode.SPREAD_REFLECT, gradient.getSpreadMode());
        assertEquals(PosMode.MODE_BOX, gradient.getPosMode());
        gradient.setPosMode(PosMode.MODE_USERSPACE);
        assertEquals(PosMode.MODE_USERSPACE, gradient.getPosMode());
        Element element = gradient.convertToSVGElement(canvas, document, canvas.getGeomDoubleConverter());
        assertEquals(PosMode.MODE_USERSPACE, element.getAttribute("gradientUnits"));
        assertEquals(SVGBaseGradient.SpreadMode.SPREAD_REFLECT, element.getAttribute("spreadMethod"));
        assertEquals(2, element.getChildNodes().getLength());
    }


}
