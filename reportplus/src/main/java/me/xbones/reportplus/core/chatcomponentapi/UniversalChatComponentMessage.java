package me.xbones.reportplus.core.chatcomponentapi;


import com.github.fernthedev.fernapi.universal.data.chat.ClickData;
import com.github.fernthedev.fernapi.universal.data.chat.HoverData;
import com.github.fernthedev.fernapi.universal.data.chat.TextMessage;
import me.xbones.reportplus.core.IChatComponent;

public class UniversalChatComponentMessage implements IChatComponent {

    private String text;
    private TextMessage component;

    public UniversalChatComponentMessage(String text){
        this.text=text;
        component = new TextMessage(text);
    }

    public String getText() {
        return text;
    }

    public TextMessage getComponent() {
        return component;
    }

    public void addHover(String hover){
        component.setHoverData(new HoverData(HoverData.Action.SHOW_TEXT, new TextMessage(text)));
    }

    @Override
    public void addClick(ClickData.Action action, String value) {
        component.setClickData(new ClickData(ClickData.Action.valueOf(action.toString()),value));
    }



}
