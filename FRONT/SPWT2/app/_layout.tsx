import { Stack } from "expo-router";
import "./globals.css";
import { AuthProvider } from '../context/AuthContext';
import { StatusBar } from "react-native";

export default function RootLayout() {
    return (
        <AuthProvider>
            <StatusBar hidden={true} />
            <Stack screenOptions={{ headerShown: false }}>
                <Stack.Screen name="(tabs)" />
            </Stack>
        </AuthProvider>
    );
}

{/*                <Stack.Screen
                    name="BoatRoute/[id]"
                    options={{
                        headerShown: false,
                    }}
                />*/}
