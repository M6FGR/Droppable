package M6FGR.droppable.client.gui.screen;

import M6FGR.droppable.network.DataGeneratorPayLoad;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.components.ObjectSelectionList;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.fml.ModContainer;
import net.neoforged.neoforge.network.PacketDistributor;
import org.lwjgl.glfw.GLFW;
import yesman.epicfight.registry.EpicFightRegistries;
import yesman.epicfight.skill.SkillCategories;
import yesman.epicfight.skill.SkillCategory;

import java.util.HashMap;
import java.util.Map;

public class LootsEditScreen extends Screen {
    private EditBox skillSearch;
    private EditBox entitySearch;
    private EditBox chanceInput;
    private SkillList skillListWidget;
    private final Map<String, Float> addedSkills = new HashMap<>();

    public LootsEditScreen(ModContainer container, Screen lastScreen) {
        super(Component.literal("Lootable SkillBooks Editor"));
    }

    @Override
    protected void init() {
        int leftX = this.width / 2 - 180;
        int boxWidth = 200;
        int bottomY = this.height - 30;

        // 1. Entity ID Bar (Vanilla Only)
        this.entitySearch = new EditBox(this.font, leftX, 40, boxWidth, 20, Component.literal("Entity ID..."));
        this.addRenderableWidget(this.entitySearch);

        // 2. Skill ID Bar
        this.skillSearch = new EditBox(this.font, leftX, 80, boxWidth, 20, Component.literal("Skill ID..."));
        this.addRenderableWidget(this.skillSearch);

        // 3. Add Button
        this.addRenderableWidget(Button.builder(Component.literal("Add"), (button) -> {
            addSkillFromInput();
        }).bounds(leftX + boxWidth + 5, 80, 40, 20).build());

        // 4. Chance Input
        this.chanceInput = new EditBox(this.font, leftX, 120, 60, 20, Component.literal("Chance"));
        this.chanceInput.setValue("0.05");
        this.addRenderableWidget(this.chanceInput);

        // 5. Skill Pool List
        this.skillListWidget = new SkillList(this, leftX + boxWidth + 55, 40, 200, 120);
        this.addRenderableWidget(this.skillListWidget);

        // --- Bottom Controls ---

        // Done Button (Centered)
        this.addRenderableWidget(Button.builder(CommonComponents.GUI_DONE, (button) -> {
            this.onClose();
        }).bounds(this.width / 2 - 100, bottomY, 200, 20).build());

        // Clear Button
        this.addRenderableWidget(Button.builder(Component.literal("Clear"), (button) -> {
            addedSkills.clear();
            skillListWidget.refreshList();
        }).bounds(this.width / 2 - 180, 180, 45, 20).build());

        // Generate Button
        this.addRenderableWidget(Button.builder(Component.literal("Generate Config"), (button) -> {
            generateConfig();
        }).bounds(this.width / 2 - 60, 180, 150, 20).build());
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        this.renderBackground(guiGraphics, mouseX, mouseY, partialTick);

        String ent = entitySearch.getValue();
        this.entitySearch.setTextColor(ent.isEmpty() || isEntityValid(ent) ? 0xFFFFFF : 0xFF5555);

        String skl = skillSearch.getValue();
        this.skillSearch.setTextColor(skl.isEmpty() || isSkillValid(skl) ? 0xFFFFFF : 0xFF5555);

        String chn = chanceInput.getValue();
        this.chanceInput.setTextColor(chn.isEmpty() || isChanceValid(chn) ? 0xFFFFFF : 0xFF5555);

        super.render(guiGraphics, mouseX, mouseY, partialTick);

        if (this.skillSearch.isFocused()) renderGhostText(guiGraphics, this.skillSearch, getTopSkillMatch());
        if (this.entitySearch.isFocused()) renderGhostText(guiGraphics, this.entitySearch, getTopEntityMatch());

        int leftX = this.width / 2 - 180;
        int boxWidth = 200;

        guiGraphics.drawString(this.font, "Entity ID:", leftX, 30, 0xAAAAAA);
        guiGraphics.drawString(this.font, "Skill:", leftX, 70, 0xAAAAAA);
        guiGraphics.drawString(this.font, "Chance (0.01 - 1.0):", leftX, 110, 0xAAAAAA);
        guiGraphics.drawString(this.font, "Pool:", leftX + boxWidth + 55, 30, 0xFFFFFF);

        guiGraphics.drawCenteredString(this.font, this.title, this.width / 2, 10, 0xFFFFFF);
    }

    private void addSkillFromInput() {
        String match = getTopSkillMatch();
        if (!match.isEmpty()) {
            addedSkills.put(match, 1.0f);
            skillSearch.setValue("");
            skillListWidget.refreshList();
        }
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        if (keyCode == GLFW.GLFW_KEY_TAB) {
            if (this.skillSearch.isFocused()) {
                this.skillSearch.setValue(getTopSkillMatch());
                return true;
            } else if (this.entitySearch.isFocused()) {
                this.entitySearch.setValue(getTopEntityMatch());
                return true;
            }
        }
        if (keyCode == GLFW.GLFW_KEY_ENTER || keyCode == GLFW.GLFW_KEY_KP_ENTER) {
            if (this.skillSearch.isFocused()) {
                addSkillFromInput();
                return true;
            }
        }
        return super.keyPressed(keyCode, scanCode, modifiers);
    }

    class SkillList extends ObjectSelectionList<SkillList.Entry> {
        public SkillList(LootsEditScreen parent, int x, int y, int width, int height) {
            super(parent.minecraft, width, height, y, 14);
            this.setX(x);
        }

        public void refreshList() {
            this.clearEntries();
            addedSkills.keySet().forEach(skill -> this.addEntry(new Entry(skill)));
        }

        class Entry extends ObjectSelectionList.Entry<Entry> {
            private final String skillName;
            public Entry(String name) { this.skillName = name; }

            @Override
            public void render(GuiGraphics g, int index, int top, int left, int width, int height, int mouseX, int mouseY, boolean isHovered, float pt) {
                g.drawString(minecraft.font, "- " + skillName, left + 12, top + 2, 0x00FF00);
            }

            @Override
            public Component getNarration() { return Component.literal(skillName); }
        }
    }

    private void generateConfig() {
        String entityId = entitySearch.getValue();
        String chanceStr = chanceInput.getValue();

        if (!isEntityValid(entityId) || !isChanceValid(chanceStr) || addedSkills.isEmpty()) {
            return; // Block generation if red
        }

        float chance = Float.parseFloat(chanceStr);
        PacketDistributor.sendToServer(new DataGeneratorPayLoad(entityId, addedSkills, chance));
        this.onClose();
    }

    private void renderGhostText(GuiGraphics graphics, EditBox box, String suggestion) {
        String typed = box.getValue();
        if (!typed.isEmpty() && suggestion.toLowerCase().startsWith(typed.toLowerCase())) {
            graphics.drawString(this.font, suggestion, box.getX() + 4, box.getY() + (box.getHeight() - 8) / 2, 0x606060, false);
        }
    }

    private String getTopSkillMatch() {
        String input = this.skillSearch.getValue().toLowerCase();
        if (input.isEmpty()) return "";
        return EpicFightRegistries.SKILL.stream()
                .filter(skill -> {
                    SkillCategory cat = skill.getCategory();
                    return cat != SkillCategories.WEAPON_PASSIVE && cat != SkillCategories.WEAPON_INNATE;
                })
                .map(skill -> skill.getRegistryName().toString())
                .filter(name -> name.startsWith(input))
                .findFirst().orElse("");
    }

    private String getTopEntityMatch() {
        String input = this.entitySearch.getValue().toLowerCase();
        if (input.isEmpty()) return "";

        return BuiltInRegistries.ENTITY_TYPE.keySet().stream()
                .filter(location -> location.getNamespace().equals("minecraft"))
                .map(ResourceLocation::getPath)
                .filter(name -> name.startsWith(input))
                .findFirst()
                .orElse("");
    }

    private boolean isEntityValid(String input) {
        return BuiltInRegistries.ENTITY_TYPE.keySet().stream()
                .anyMatch(loc -> loc.getNamespace().equals("minecraft") && loc.getPath().equals(input));
    }

    private boolean isSkillValid(String input) {
        return EpicFightRegistries.SKILL.stream()
                .anyMatch(skill -> skill.getRegistryName().toString().equals(input));
    }

    private boolean isChanceValid(String input) {
        try {
            float f = Float.parseFloat(input);
            return f > 0.0f && f <= 1.0f;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}