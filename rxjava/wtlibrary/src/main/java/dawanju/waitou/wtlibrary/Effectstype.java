package dawanju.waitou.wtlibrary;

import dawanju.waitou.wtlibrary.effects.BaseEffects;
import dawanju.waitou.wtlibrary.effects.FadeIn;
import dawanju.waitou.wtlibrary.effects.Fall;
import dawanju.waitou.wtlibrary.effects.FlipH;
import dawanju.waitou.wtlibrary.effects.FlipV;
import dawanju.waitou.wtlibrary.effects.NewsPaper;
import dawanju.waitou.wtlibrary.effects.RotateBottom;
import dawanju.waitou.wtlibrary.effects.RotateLeft;
import dawanju.waitou.wtlibrary.effects.Shake;
import dawanju.waitou.wtlibrary.effects.SideFall;
import dawanju.waitou.wtlibrary.effects.SlideLeft;
import dawanju.waitou.wtlibrary.effects.SlideRight;
import dawanju.waitou.wtlibrary.effects.SlideTop;
import dawanju.waitou.wtlibrary.effects.Slit;

/**
 * Created by waitou on 16/8/31.
 * 动画枚举
 */
public enum Effectstype {
    Fadein(FadeIn.class),
    Slideleft(SlideLeft.class),
    Slidetop(SlideTop.class),
    Slideright(SlideRight.class),
    Fall(Fall.class),
    Newspager(NewsPaper.class),
    Fliph(FlipH.class),
    Flipv(FlipV.class),
    RotateBottom(RotateBottom.class),
    RotateLeft(RotateLeft.class),
    Slit(Slit.class),
    Shake(Shake.class),
    Sidefill(SideFall.class);

    private Class<? extends BaseEffects> effectsClazz;

    Effectstype(Class<? extends BaseEffects> effectsClazz) {
        this.effectsClazz = effectsClazz;
    }

    public BaseEffects getEffectsClazz() {
        BaseEffects effects = null;
        try {
            effects = effectsClazz.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return effects;
    }
}
