package com.myflappybird.frameworkhelper;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.ImageTextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

import static com.badlogic.gdx.graphics.Color.WHITE;

public class CreateLB {

    public static ImageTextButton createImageTextButton(String text, Texture upTexture, Texture downTexture, Texture iconTexture) {
        TextureRegionDrawable upDrawable = new TextureRegionDrawable(new TextureRegion(upTexture));
        TextureRegionDrawable downDrawable = new TextureRegionDrawable(new TextureRegion(downTexture));
        ImageTextButton.ImageTextButtonStyle style = new ImageTextButton.ImageTextButtonStyle();
        style.up = upDrawable;
        style.down = downDrawable;
        style.font = AssetLoader.font;
        style.font.getData().setScale(0.8f, -1);
        if (iconTexture != null) {
            TextureRegionDrawable iconDrawable = new TextureRegionDrawable(new TextureRegion(iconTexture));
            style.imageUp = iconDrawable;
        }
        return new ImageTextButton(text, style);
    }

    public static Label createLabel(String text) {
        return new Label(text, new Label.LabelStyle(AssetLoader.font, WHITE));
    }

    public static CheckBox createCheckBox(Texture checkBoxOffTexture, Texture checkBoxOnTexture) {
        CheckBox.CheckBoxStyle style = new CheckBox.CheckBoxStyle();
        style.checkboxOff = new TextureRegionDrawable(new TextureRegion(checkBoxOffTexture));
        style.checkboxOn = new TextureRegionDrawable(new TextureRegion(checkBoxOnTexture));
        style.font = AssetLoader.font;

        return new CheckBox("", style);
    }
    public static Slider createSlider(Texture sliderBgTexture, Texture sliderKnobTexture) {
        Slider.SliderStyle style = new Slider.SliderStyle();
        style.background = new TextureRegionDrawable(new TextureRegion(sliderBgTexture));
        style.knob = new TextureRegionDrawable(new TextureRegion(sliderKnobTexture));
        return new Slider(0, 1, 0.1f, false, style);
    }
}
