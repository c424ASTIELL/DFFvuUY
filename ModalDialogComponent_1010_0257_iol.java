// 代码生成时间: 2025-10-10 02:57:17
package com.example.micronautapp.components;

import io.micronaut.views.ViewsRoute;
import io.micronaut.views.model.ModelAndView;
import javax.inject.Singleton;

// 模态对话框组件
@ViewsRoute("/modal")
@Singleton
public class ModalDialogComponent {

    // 显示模态对话框
    public ModelAndView show(String title, String message) {
        // 错误处理：检查标题和消息是否为null
        if (title == null || title.trim().isEmpty()) {
            throw new IllegalArgumentException("标题不能为空");
        }
        if (message == null || message.trim().isEmpty()) {
            throw new IllegalArgumentException("消息不能为空");
        }

        // 创建ModelAndView对象，传递标题和消息作为视图模型数据
        ModelAndView modelAndView = new ModelAndView("modalDialog", "title": title, "message": message);
        return modelAndView;
    }
}
