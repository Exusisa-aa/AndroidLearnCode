package com.film.backend.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.film.backend.entity.Hall;
import com.film.backend.mapper.HallMapper;
import com.film.backend.service.IHallService;
import org.springframework.stereotype.Service;

@Service
public class HallServiceImpl extends ServiceImpl<HallMapper, Hall> implements IHallService {
}
