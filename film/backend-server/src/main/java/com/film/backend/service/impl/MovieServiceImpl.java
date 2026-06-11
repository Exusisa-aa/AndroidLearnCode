package com.film.backend.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.film.backend.entity.Movie;
import com.film.backend.mapper.MovieMapper;
import com.film.backend.service.IMovieService;
import org.springframework.stereotype.Service;

@Service
public class MovieServiceImpl extends ServiceImpl<MovieMapper, Movie> implements IMovieService {
}
