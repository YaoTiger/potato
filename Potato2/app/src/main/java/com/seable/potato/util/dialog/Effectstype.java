package com.seable.potato.util.dialog;


/**
 * @author 王维玉copy
 * @ClassName: Effectstype
 * @Description: dialog相关
 * @date 2015-01-17 15:32
 */
public enum Effectstype {

    Slidetop(SlideTop.class),
    Shake(Shake.class),
    SlideRight(SlideRight.class),
    SlideBottom(SlideBottom.class);
    private Class<? extends BaseEffects> effectsClazz;

    private Effectstype(Class<? extends BaseEffects> mclass) {
        effectsClazz = mclass;
    }

    public BaseEffects getAnimator() {
        BaseEffects bEffects = null;
        try {
            bEffects = effectsClazz.newInstance();
        } catch (ClassCastException e) {
            throw new Error("Can not init animatorClazz instance");
        } catch (InstantiationException e) {
            // TODO Auto-generated catch block
            throw new Error("Can not init animatorClazz instance");
        } catch (IllegalAccessException e) {
            // TODO Auto-generated catch block
            throw new Error("Can not init animatorClazz instance");
        }
        return bEffects;
    }
}
