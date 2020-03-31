package com.propertytycoonmakers.make;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

public class GameOptions {

    private static final String PREF_MUSIC_VOLUME = "volume";
    private static final String PREF_MUSIC_ENABLED = "music.enabled";
    private static final String PREF_FX_ENABLED = "sound.enabled";
    private static final String PREF_FX_VOL = "sound";
    private static final String PREFS_NAME = "Property Tycoon";

    protected Preferences getPrefs() {
        return Gdx.app.getPreferences(PREFS_NAME);
    }

    public float getMusicVolume() {
        return getPrefs().getFloat(PREF_MUSIC_VOLUME, 0.5f);
    }

    public void setMusicVolume(float volume) {
        getPrefs().putFloat(PREF_MUSIC_VOLUME, volume);
        getPrefs().flush();
    }

    public boolean isMusicEnabled() {
        return getPrefs().getBoolean(PREF_MUSIC_ENABLED, true);
    }

    public void setMusicEnabled(boolean musicEnabled) {
        getPrefs().putBoolean(PREF_MUSIC_ENABLED, musicEnabled);
        getPrefs().flush();
    }

    public float getFxVolume() {
        return getPrefs().getFloat(PREF_FX_VOL, 0.5f);
    }

    public void setFxVolume(float volume) {
        getPrefs().putFloat(PREF_FX_VOL, volume);
        getPrefs().flush();
    }

    public boolean isFxEnabled() {
        return getPrefs().getBoolean(PREF_FX_ENABLED, true);
    }

    public void setFxEnabled(boolean fxEnabled) {
        getPrefs().putBoolean(PREF_FX_ENABLED, fxEnabled);
        getPrefs().flush();
    }
}
