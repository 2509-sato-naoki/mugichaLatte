package com.example.mugichaLatte.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

//Spring の管理対象（DI コンテナ管理下）に登録するアノテーション
@Component
public class LoginFilter extends OncePerRequestFilter {  //←OncePerRequestFilterとは1回のリクエストごとに1回だけ必ず実行されるフィルター。（同じリクエストで複数回呼ばれないようになっている）

    @Override //親クラス OncePerRequestFilter のメソッドを オーバーライドしていることを明示。
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) //次のフィルター・コントローラーに処理をわたすためのもの
            throws ServletException, IOException {

        String path = request.getServletPath();
        System.out.println("PATH = " + path);

        // ★ ログインページ+CSSは除外(これがないとログイン画面に入れない）
        if(path.equals("/login") || path.startsWith("/login") || path.startsWith("/css")) {
            filterChain.doFilter(request, response);
            return;
        }



        // ログイン中のユーザー（認証情報）を取得
        //①現在のスレッド（リクエスト）に紐づくSecurityContext(ログイン情報を保持しているオブジェクト) を取得する。
        //②SecurityContextの中からAuthentication（認証情報）を取り出すメソッド
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        //取得した認証情報がない｜｜認証情報が有効でないとき｜｜principalが"anonymousUser"のとき
        if (auth == null || !auth.isAuthenticated() || auth.getPrincipal().equals("anonymousUser")) {
            //未ログイン→セッションにエラーを入れてログイン画面へ
            List<String> errors = new ArrayList<>();
            errors.add("ログインしてください");
            request.setAttribute("errorMessages", errors);
            request.getRequestDispatcher("/login").forward(request, response);
            return;
        }

        // 通常処理
        filterChain.doFilter(request, response);
    }
}
