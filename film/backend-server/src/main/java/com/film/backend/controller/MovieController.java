package com.film.backend.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.film.backend.common.Result;
import com.film.backend.entity.Movie;
import com.film.backend.service.IMovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/movie")
public class MovieController {

    @Autowired
    private IMovieService movieService;

    @GetMapping("/list")
    public Result<List<Movie>> getMovieList(@RequestParam(required = false, defaultValue = "1") Integer status) {
        LambdaQueryWrapper<Movie> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Movie::getStatus, status);
        queryWrapper.orderByDesc(Movie::getReleaseDate);
        return Result.success(movieService.list(queryWrapper));
    }

    @GetMapping("/{id}")
    public Result<Movie> getMovieDetail(@PathVariable Long id) {
        return Result.success(movieService.getById(id));
    }
}
