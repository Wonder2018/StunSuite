package top.imwonder.stunsuite.core;

public enum NatType {
    UDP_BLOCK, // udp阻塞
    SYSTEM_ERROR,// 系统错误
    FULL_CONE, // 全锥型
    RESTRICTED_CONE, // IP受限锥型
    PORT_RESTRICTED_CONE, // 端口受限锥型
    SYMMETRIC; // 对称型
}