import {View, Text, StyleSheet} from 'react-native';
import React from 'react'
import {Stack, Tabs} from "expo-router";
import { Ionicons } from "@expo/vector-icons";
import '../globals.css';

export default function TabsLayout() {
    return (
        <Tabs
            screenOptions={{
                headerShown: false,
                tabBarShowLabel: false,
                tabBarActiveTintColor: "#2563eb",
                tabBarInactiveTintColor: "#9ca3af",
                tabBarStyle: {
                    height: 60,
                    paddingTop: 5,
                    paddingBottom: 5,
                },
                tabBarIconStyle: {
                    marginBottom: 0,
                },
            }}
        >
            <Tabs.Screen
                name="index"
                options={{
                    tabBarIcon: ({ color, size }) => (
                        <Ionicons name="home" size={28} color={color} />
                    ),
                }}
            />
            <Tabs.Screen
                name="favorites"
                options={{
                    tabBarIcon: ({ color, size }) => (
                        <Ionicons name="heart" size={28} color={color} />
                    ),
                }}
            />
            <Tabs.Screen
                name="bookings"
                options={{
                    tabBarIcon: ({ color, size }) => (
                        <Ionicons name="ticket-outline" size={28} color={color} />
                    ),
                }}
            />
            <Tabs.Screen
                name="profile"
                options={{
                    tabBarIcon: ({ color, size }) => (
                        <Ionicons name="person" size={28} color={color} />
                    ),
                }}
            />
        </Tabs>
    );
}