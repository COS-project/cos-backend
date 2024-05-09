package com.cos.cercat.apis.global.resolver;

import com.cos.cercat.common.annotation.CursorDefault;
import com.cos.cercat.common.domain.Cursor;
import com.cos.cercat.common.domain.SortDirection;
import com.cos.cercat.common.domain.SortOrder;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import java.util.ArrayList;
import java.util.List;

public class CursorArgumentResolver implements HandlerMethodArgumentResolver {

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterType().equals(Cursor.class)
                && parameter.hasParameterAnnotation(CursorDefault.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest, WebDataBinderFactory binderFactory) {
        CursorDefault cursorDefault = parameter.getParameterAnnotation(CursorDefault.class);

        String pageParam = webRequest.getParameter("page");
        int page = (pageParam != null) ? Integer.parseInt(pageParam) : cursorDefault.page();

        String sizeParam = webRequest.getParameter("size");
        int size = (sizeParam != null) ? Integer.parseInt(sizeParam) : cursorDefault.size();

        List<SortOrder> sortOrders = new ArrayList<>();
        String sortFieldsParam = webRequest.getParameter("sortFields");
        String sortDirectionsParam = webRequest.getParameter("sortDirections");

        if (sortFieldsParam != null && sortDirectionsParam != null) {
            String[] fields = sortFieldsParam.split(",");
            String[] directions = sortDirectionsParam.split(",");
            for (int i = 0; i < fields.length; i++) {
                sortOrders.add(new SortOrder(fields[i].trim(), SortDirection.valueOf(directions[i].trim().toUpperCase())));
            }

            return new Cursor(page, size, sortOrders);
        }

        String[] fields = cursorDefault.sortFields().split(",");
        String[] directions = cursorDefault.sortDirections().split(",");
        for (int i = 0; i < fields.length; i++) {
            sortOrders.add(new SortOrder(fields[i].trim(), SortDirection.valueOf(directions[i].trim().toUpperCase())));
        }
        return new Cursor(page, size, sortOrders);
    }
}
