package me.xbones.reportplus.core;

import com.github.fernthedev.fernapi.universal.data.chat.ClickData;

public interface IChatComponent {

    public String getText();

    public Object getComponent();

    public void addHover(String hover);

    public void addClick(ClickData.Action action, String value);


}
