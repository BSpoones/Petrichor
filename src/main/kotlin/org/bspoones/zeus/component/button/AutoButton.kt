package org.bspoones.zeus.component.button

import net.dv8tion.jda.api.entities.emoji.Emoji
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent
import net.dv8tion.jda.api.interactions.components.buttons.ButtonStyle
import net.dv8tion.jda.internal.interactions.component.ButtonImpl
import org.bspoones.zeus.component.ComponentRegistry
import org.bspoones.zeus.extensions.toButton

/**
 * **AutoButton**
 *
 * An easy way to make Discord Button Components!
 *
 * ```kotlin
 * @SlashCommand("name", "description")
 * fun onCommand(
 *     event: SlashCommandInteractionEvent
 * ) {
 *     val button = AutoButton.Builder()
 *         .setButtonStyle(ButtonStyle.PRIMARY)
 *         .setCustomId("example")
 *         .setLabel("Example Label")
 *         .setOnClick { event: ButtonInteractEvent ->
 *             // Button behaviour here
 *         }
 *         .build()
 *
 *     val = MessageCreateBuilder()
 *         .setContent("Example message")
 *         .addActionRow(button.toButton())
 *         .build()
 *
 *     event.reply(message).queue()
 * }
 * ```
 * @see org.bspoones.zeus.command.annotations.command.SlashCommand
 * @see [ButtonInteractionEvent]
 *
 * @author <a href="https://www.bspoones.com">BSpoones</a>
 */
class AutoButton private constructor(
    val buttonStyle: ButtonStyle,
    val customId: String,
    val label: String,
    val disabled: Boolean,
    val emoji: Emoji?,
    val onClick: ((ButtonInteractionEvent) -> Unit)?
) {
    /**
     * AutoButton Builder
     *
     * ```kotlin
     * val button = AutoButton.Builder()
     *     .setButtonStyle(ButtonStyle.PRIMARY)
     *     .setCustomId("example")
     *     .setLabel("Example Label")
     *     .setDisabled(false)
     *     .setEmoji(Emoji.fromUnicode("🥄"))
     *     .setOnClick { event: ButtonInteractEvent ->
     *         // Button behaviour here
     *     }
     *     .build()
     * ```
     *
     * @see [Emoji]
     * @see [ButtonInteractionEvent]
     *
     * @author <a href="https://www.bspoones.com">BSpoones</a>
     */
    class Builder {
        private var buttonStyle: ButtonStyle = ButtonStyle.PRIMARY
        private var customId: String = ""
        private var label: String = ""
        private var disabled: Boolean = false
        private var emoji: Emoji? = null
        private var onClick: ((ButtonInteractionEvent) -> Unit)? = null

        /**
         * Button Style
         *
         * @see <img alt="ButtonExample" src="https://raw.githubusercontent.com/discord-jda/JDA/52377f69d1f3bfba909c51a449ac6b258f606956/assets/wiki/interactions/ButtonExamples.png">
         */
        fun setButtonStyle(buttonStyle: ButtonStyle) = apply { this.buttonStyle = buttonStyle }
        fun setCustomId(customId: String) = apply { this.customId = customId }
        fun setLabel(label: String) = apply { this.label = label }
        fun setDisabled(disabled: Boolean) = apply { this.disabled = disabled }
        fun setEmoji(emoji: Emoji?) = apply { this.emoji = emoji }
        fun setOnClick(onClick: ((ButtonInteractionEvent) -> Unit)?) = apply { this.onClick = onClick }

        fun build(): AutoButton {
            if (onClick != null) {
                ComponentRegistry.buttonMap[customId] = onClick!!
            }

            return AutoButton(buttonStyle, customId, label, disabled, emoji, onClick)
        }
    }

    /**
     * Creates a discord button
     *
     * @return [ButtonImpl] - Button instance
     * @author <a href="https://www.bspoones.com">BSpoones</a>
     */
    fun toButton(): ButtonImpl {
        return this.buttonStyle.toButton(
            customId, label, disabled, emoji
        )
    }

    companion object {
        /**
         * Creates a new AutoButton instance with the specified parameters.
         *
         * @param buttonStyle The style of the button. Default is ButtonStyle.PRIMARY.
         * @param customId The custom ID of the button. Default is an empty string.
         * @param label The label text of the button. Default is an empty string.
         * @param disabled Whether the button should be disabled. Default is false.
         * @param emoji The emoji to be displayed on the button. Default is null.
         * @param onClick The function to be executed when the button is clicked. Default is null.
         * @return A new AutoButton instance configured with the specified parameters.
         *
         * @see [ButtonStyle]
         * @author <a href="https://www.bspoones.com">BSpoones</a>
         */
        fun of(
            buttonStyle: ButtonStyle = ButtonStyle.PRIMARY,
            customId: String = "",
            label: String = "",
            disabled: Boolean = false,
            emoji: Emoji? = null,
            onClick: ((ButtonInteractionEvent) -> Unit)? = null
        ): AutoButton {
            return Builder()
                .setButtonStyle(buttonStyle)
                .setCustomId(customId)
                .setLabel(label)
                .setDisabled(disabled)
                .setEmoji(emoji)
                .setOnClick(onClick)
                .build()
        }
    }
}
