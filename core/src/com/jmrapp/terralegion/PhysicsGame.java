package com.jmrapp.terralegion;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.jmrapp.terralegion.engine.utils.Settings;
import com.jmrapp.terralegion.engine.utils.Timer;
import com.jmrapp.terralegion.engine.views.drawables.ResourceManager;
import com.jmrapp.terralegion.engine.views.screens.ScreenManager;
import com.jmrapp.terralegion.game.utils.ThreadManager;
import com.jmrapp.terralegion.game.views.screen.MainScreen;

public class PhysicsGame implements ApplicationListener {

    protected SpriteBatch sb;
    protected boolean paused;
    protected long lastBack;

    @Override
    public void create() {
        sb = new SpriteBatch(150);
        sb.enableBlending();
        instantiateSettings();
        Gdx.input.setCatchBackKey(true); //Stops from exiting when back button pressed

        //MENU
        ResourceManager.getInstance().loadSprite("menuBg", "menu/bg.png", .588f, .625f);
        ResourceManager.getInstance().loadSprite("btnBg", "ui/btnBg.png", .75f, .75f);

        //GAME
        ResourceManager.getInstance().loadTexture("joystickBg", "ui/joystickBg.png");
        ResourceManager.getInstance().loadTexture("joystickKnob", "ui/joystickKnob.png");
        ResourceManager.getInstance().loadTexture("bg", "bg.png");
        ResourceManager.getInstance().loadTexture("player", "player.png");
        ResourceManager.getInstance().loadTexture("playerAnimated", "player_animated.png");

        ResourceManager.getInstance().loadTexture("leftBtn", "ui/leftBtn.png");
        ResourceManager.getInstance().loadTexture("rightBtn", "ui/rightBtn.png");
        ResourceManager.getInstance().loadTexture("upBtn", "ui/upBtn.png");
        ResourceManager.getInstance().loadTexture("bunny", "entities/bunny.png");

        //UI
        ResourceManager.getInstance().loadTexture("inventoryBox", "ui/inventoryBox.png");
        ResourceManager.getInstance().loadTexture("selectedInventoryBox", "ui/selectedInventoryBox.png");
        ResourceManager.getInstance().loadTexturedDrawable("cancelBtn", "ui/cancelBtn.png");
        ResourceManager.getInstance().loadTexturedDrawable("unselectedCraftingBtn", "ui/unselectedCraftingBtn.png");
        ResourceManager.getInstance().loadTexturedDrawable("unselectedInvBtn", "ui/unselectedInvBtn.png");
        ResourceManager.getInstance().loadTexturedDrawable("selectedInvBtn", "ui/selectedInvBtn.png");
        ResourceManager.getInstance().loadTexturedDrawable("dropItemBtn", "ui/dropItemBtn.png");
        ResourceManager.getInstance().loadTexturedDrawable("splitItemBtn", "ui/splitItemBtn.png");
        ResourceManager.getInstance().loadTexture("heart", "ui/heart.png");

        //INVENTORY SCREEN
        ResourceManager.getInstance().loadTexture("inventoryBg", "inventory/invBg.png");
        ResourceManager.getInstance().loadTexture("invStageBg", "inventory/stageBg.png");
        ResourceManager.getInstance().loadTexturedDrawable("inventoryBtn", "ui/inventoryBtn.png");

        //CRAFTING SCREEN
        ResourceManager.getInstance().loadTexture("craftingBg", "inventory/craftingBg.png");
        ResourceManager.getInstance().loadTexturedDrawable("selectedCraftingBtn", "ui/selectedCraftingBtn.png");
        ResourceManager.getInstance().loadTexture("selectedToolCategoryBtn", "ui/selectedToolCategoryBtn.png");
        ResourceManager.getInstance().loadTexture("unselectedToolCategoryBtn", "ui/unselectedToolCategoryBtn.png");
        ResourceManager.getInstance().loadTexturedDrawable("craftBtn", "ui/craftBtn.png");

        TextureAtlas atlas = new TextureAtlas(Gdx.files.internal("tiles/tiles.atlas"));
        //TILES
        ResourceManager.getInstance().loadAtlasRegionDrawable("grass", atlas, "grass");
        ResourceManager.getInstance().loadAtlasRegionDrawable("dirt", atlas, "dirt");
        ResourceManager.getInstance().loadAtlasRegionDrawable("stone", atlas, "stone");
        ResourceManager.getInstance().loadAtlasRegionDrawable("diamond", atlas, "diamond");
        ResourceManager.getInstance().loadAtlasRegionDrawable("torch", atlas, "torch");
        ResourceManager.getInstance().loadAtlasRegionDrawable("coal", atlas, "coal");
        ResourceManager.getInstance().loadAtlasRegionDrawable("stoneWall", atlas, "stoneWall");
        ResourceManager.getInstance().loadAtlasRegionDrawable("dirtWall", atlas, "dirtWall");
        ResourceManager.getInstance().loadAtlasRegionDrawable("leaves", atlas, "leaves");
        ResourceManager.getInstance().loadAtlasRegionDrawable("wood", atlas, "wood");
        ResourceManager.getInstance().loadAtlasRegionDrawable("dummyTile", atlas, "dummyTile");
        ResourceManager.getInstance().loadAtlasRegionDrawable("blankTile", atlas, "blankTile");
        ResourceManager.getInstance().loadAtlasRegionDrawable("chest", atlas, "chest");

        //DROPS
        ResourceManager.getInstance().loadAtlasRegionDrawable("dirtDrop", atlas, "dirtDrop");
        ResourceManager.getInstance().loadAtlasRegionDrawable("grassDrop", atlas, "grassDrop");
        ResourceManager.getInstance().loadAtlasRegionDrawable("stoneDrop", atlas, "stoneDrop");
        ResourceManager.getInstance().loadAtlasRegionDrawable("torchDrop", atlas, "torchDrop");
        ResourceManager.getInstance().loadAtlasRegionDrawable("diamondDrop", atlas, "diamondDrop");
        ResourceManager.getInstance().loadAtlasRegionDrawable("coalDrop", atlas, "coalDrop");
        ResourceManager.getInstance().loadAtlasRegionDrawable("leavesDrop", atlas, "leavesDrop");
        ResourceManager.getInstance().loadAtlasRegionDrawable("woodDrop", atlas, "woodDrop");
        ResourceManager.getInstance().loadTexturedDrawable("stickDrop", "tiles/drops/stickDrop.png");

        //ITEMS
        ResourceManager.getInstance().loadTexturedDrawable("woodenPickaxe", "items/woodenPickaxe.png");
        ResourceManager.getInstance().loadTexturedDrawable("sword", "items/sword.png");
        ResourceManager.getInstance().loadTexturedDrawable("stick", "items/stick.png");

        //FONTS
        ResourceManager.getInstance().loadFont("font", "fonts/source-sans-pro-regular.ttf", Color.WHITE, 20);
        ResourceManager.getInstance().loadFont("smallFont", "fonts/source-sans-pro-regular.ttf", Color.WHITE, 12);

        //SOUNDS
        //SoundManager.getInstance().loadSound("buttonClick", "sounds/buttonClick.wav");

		/*if (!PrefManager.stringPrefExists("levelCompleted11")) {
            PrefManager.putEncodedString("levelCompleted11", "1-1");
		}*/

        //ItemManager.getInstance(); //LOAD THE INSTANCE
        //DropManager.getInstance(); //LOAD THE INSTANCE
        ScreenManager.setScreen(new MainScreen());
    }

    @Override
    public void render() {
        if (!paused) {
            Timer.getInstance().update();

            if (ScreenManager.getCurrent() != null)
                ScreenManager.getCurrent().update();
        }
        if (Gdx.input.isKeyPressed(Input.Keys.BACK) && System.currentTimeMillis() - lastBack > 300) {
            if (ScreenManager.getCurrent() != null) {
                ScreenManager.getCurrent().onBackPressed();
            }
            lastBack = System.currentTimeMillis();
        }

        Gdx.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT);
        Gdx.gl20.glClearColor(0f, 0f, 0f, 1);

        if (ScreenManager.getCurrent() != null) {
            ScreenManager.getCurrent().render(sb);
        }
    }

    @Override
    public void resize(int width, int height) {
        if (ScreenManager.getCurrent() != null)
            ScreenManager.getCurrent().resize(width, height);
    }

    @Override
    public void pause() {
        paused = true;
        if (ScreenManager.getCurrent() != null)
            ScreenManager.getCurrent().pause();
    }

    @Override
    public void resume() {
        paused = false;
        if (ScreenManager.getCurrent() != null)
            ScreenManager.getCurrent().resume();
    }

    @Override
    public void dispose() {
        sb.dispose();
        ResourceManager.getInstance().dispose();
        ThreadManager.getInstance().shutdown();
    }

    public static void instantiateSettings() {
        Settings.addSetting("width", 800);
        Settings.addSetting("height", 480);
    }

}
