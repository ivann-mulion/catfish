
import React, { useState, useEffect } from 'react';
import {
    View,
    Text,
    TextInput,
    TouchableOpacity,
    StyleSheet,
    Alert,
    ScrollView,
    ActivityIndicator
} from 'react-native';
import { loginUser, logoutUser, deleteUser, registerUser } from '../../api/auth';
import { useAuth } from '../../context/AuthContext';

const ProfileScreen = () => {
    const { user, setUser, isAuthenticated, logout } = useAuth();
    const [isLoginMode, setIsLoginMode] = useState(true);
    const [loading, setLoading] = useState(false);
    const [formData, setFormData] = useState({
        username: '',
        firstName: '',
        lastName: '',
        email: '',
        password: '',
        phone: ''
    });

    useEffect(() => {
        if (user) {
            setFormData({
                username: user.username || '',
                firstName: user.firstName || '',
                lastName: user.lastName || '',
                email: user.email || '',
                password: '',
                phone: user.phone || ''
            });
        }
    }, [user]);

    const getErrorMessage = (error: any): string => {
        if (error instanceof Error) {
            return error.message;
        } else if (typeof error === 'string') {
            return error;
        } else if (error?.response?.data?.message) {
            return error.response.data.message;
        }
        return 'Произошла неизвестная ошибка';
    };

    const handleLogin = async (): Promise<void> => {
        if (!formData.username || !formData.password) {
            Alert.alert('Ошибка', 'Введите логин и пароль');
            return;
        }

        setLoading(true);
        try {
            const userData = await loginUser(formData.username, formData.password);
            setUser(userData);
            Alert.alert('Успех', 'Вход выполнен!');
        } catch (error: any) {
            const message = getErrorMessage(error);
            Alert.alert('Ошибка входа', message);
        } finally {
            setLoading(false);
        }
    };

    const handleRegister = async (): Promise<void> => {
        if (!formData.username || !formData.password || !formData.email) {
            Alert.alert('Ошибка', 'Заполните обязательные поля');
            return;
        }

        setLoading(true);
        try {
            const userData = await registerUser(formData);
            Alert.alert('Успех', 'Регистрация успешна! Теперь войдите в аккаунт.');

            setIsLoginMode(true);
            setFormData({
                username: formData.username,
                firstName: '',
                lastName: '',
                email: '',
                password: '',
                phone: ''
            });

        } catch (error: any) {
            const message = getErrorMessage(error);
            Alert.alert('Ошибка регистрации', message);
        } finally {
            setLoading(false);
        }
    };

    const handleLogout = async (): Promise<void> => {
        setLoading(true);
        try {
            await logoutUser();
            logout();
            Alert.alert('Успех', 'Вы вышли из аккаунта');
        } catch (error: any) {
            const message = getErrorMessage(error);
            Alert.alert('Ошибка', message);
        } finally {
            setLoading(false);
        }
    };

    const handleDeleteAccount = async (): Promise<void> => {
        if (!user) return;

        Alert.alert(
            'Удаление аккаунта',
            'Вы уверены? Это действие нельзя отменить.',
            [
                { text: 'Отмена', style: 'cancel' },
                {
                    text: 'Удалить',
                    style: 'destructive',
                    onPress: async () => {
                        setLoading(true);
                        try {
                            await deleteUser(user.username);
                            logout();
                            Alert.alert('Успех', 'Аккаунт удален');
                        } catch (error: any) {
                            const message = getErrorMessage(error);
                            Alert.alert('Ошибка', message);
                        } finally {
                            setLoading(false);
                        }
                    }
                }
            ]
        );
    };

    if (loading) {
        return (
            <View style={styles.center}>
                <ActivityIndicator size="large" color="#007AFF" />
                <Text style={{ marginTop: 10 }}>Загрузка...</Text>
            </View>
        );
    }


    const renderProfileContent = () => {
        if (!user) return null;

        return (
            <View style={styles.profileContainer}>
                <Text style={styles.title}>👤 Профиль</Text>

                <View style={styles.infoSection}>
                    <Text style={styles.label}>Имя:</Text>
                    <Text style={styles.value}>{user.firstName || 'Не указано'}</Text>
                </View>

                <View style={styles.infoSection}>
                    <Text style={styles.label}>Фамилия:</Text>
                    <Text style={styles.value}>{user.lastName || 'Не указано'}</Text>
                </View>

                <View style={styles.infoSection}>
                    <Text style={styles.label}>Email:</Text>
                    <Text style={styles.value}>{user.email || 'Не указан'}</Text>
                </View>

                <View style={styles.infoSection}>
                    <Text style={styles.label}>Телефон:</Text>
                    <Text style={styles.value}>{user.phone || 'Не указан'}</Text>
                </View>

                <View style={styles.infoSection}>
                    <Text style={styles.label}>Логин:</Text>
                    <Text style={styles.value}>{user.username}</Text>
                </View>

                <TouchableOpacity
                    style={styles.logoutButton}
                    onPress={handleLogout}
                    disabled={loading}
                >
                    <Text style={styles.buttonText}>Выйти</Text>
                </TouchableOpacity>

                <TouchableOpacity
                    style={styles.deleteButton}
                    onPress={handleDeleteAccount}
                    disabled={loading}
                >
                    <Text style={styles.deleteButtonText}>🗑️ Удалить аккаунт</Text>
                </TouchableOpacity>
            </View>
        );
    };

    // Контент для формы входа/регистрации
    const renderAuthForm = () => (
        <View style={styles.authContainer}>
            <Text style={styles.title}>
                {isLoginMode ? 'Вход в аккаунт' : 'Регистрация'}
            </Text>

            {!isLoginMode && (
                <>
                    <TextInput
                        style={styles.input}
                        placeholder="Имя"
                        value={formData.firstName}
                        onChangeText={(text) => setFormData({...formData, firstName: text})}
                        editable={!loading}
                    />

                    <TextInput
                        style={styles.input}
                        placeholder="Фамилия"
                        value={formData.lastName}
                        onChangeText={(text) => setFormData({...formData, lastName: text})}
                        editable={!loading}
                    />

                    <TextInput
                        style={styles.input}
                        placeholder="Телефон"
                        value={formData.phone}
                        onChangeText={(text) => setFormData({...formData, phone: text})}
                        keyboardType="phone-pad"
                        editable={!loading}
                    />
                </>
            )}

            <TextInput
                style={styles.input}
                placeholder="Логин *"
                value={formData.username}
                onChangeText={(text) => setFormData({...formData, username: text})}
                autoCapitalize="none"
                editable={!loading}
            />

            <TextInput
                style={styles.input}
                placeholder="Email *"
                value={formData.email}
                onChangeText={(text) => setFormData({...formData, email: text})}
                keyboardType="email-address"
                autoCapitalize="none"
                editable={!loading}
            />

            <TextInput
                style={styles.input}
                placeholder="Пароль *"
                value={formData.password}
                onChangeText={(text) => setFormData({...formData, password: text})}
                secureTextEntry
                editable={!loading}
            />

            <TouchableOpacity
                style={[styles.primaryButton, loading && styles.disabledButton]}
                onPress={isLoginMode ? handleLogin : handleRegister}
                disabled={loading}
            >
                <Text style={styles.buttonText}>
                    {loading ? 'Загрузка...' : (isLoginMode ? 'Войти' : 'Зарегистрироваться')}
                </Text>
            </TouchableOpacity>

            <TouchableOpacity
                style={[styles.switchModeButton, loading && styles.disabledButton]}
                onPress={() => {
                    setIsLoginMode(!isLoginMode);
                    setFormData(prev => ({ ...prev, password: '' }));
                }}
                disabled={loading}
            >
                <Text style={styles.switchModeText}>
                    {isLoginMode ? 'Нет аккаунта? Зарегистрироваться' : 'Уже есть аккаунт? Войти'}
                </Text>
            </TouchableOpacity>
        </View>
    );

    return (
        <View style={styles.mainContainer}>
            <ScrollView
                contentContainerStyle={styles.scrollContent}
                showsVerticalScrollIndicator={false}
            >
                {isAuthenticated ? renderProfileContent() : renderAuthForm()}
            </ScrollView>
        </View>
    );
};

const styles = StyleSheet.create({
    mainContainer: {
        flex: 1,
        backgroundColor: '#fff',
    },
    scrollContent: {
        flexGrow: 1,
        justifyContent: 'center',
        padding: 20,
    },
    center: {
        flex: 1,
        justifyContent: 'center',
        alignItems: 'center',
        backgroundColor: '#fff',
    },
    profileContainer: {
        width: '100%',
        maxWidth: 400,
        alignSelf: 'center',
    },
    authContainer: {
        width: '100%',
        maxWidth: 400,
        alignSelf: 'center',
    },
    title: {
        fontSize: 24,
        fontWeight: 'bold',
        marginBottom: 20,
        textAlign: 'center',
        color: '#000',
    },
    input: {
        borderWidth: 1,
        borderColor: '#ddd',
        padding: 15,
        marginBottom: 15,
        borderRadius: 8,
        fontSize: 16,
        backgroundColor: '#fff',
    },
    primaryButton: {
        backgroundColor: '#007AFF',
        padding: 15,
        borderRadius: 8,
        alignItems: 'center',
        marginBottom: 10,
    },
    disabledButton: {
        backgroundColor: '#ccc',
    },
    logoutButton: {
        backgroundColor: '#FF9500',
        padding: 15,
        borderRadius: 8,
        alignItems: 'center',
        marginBottom: 10,
    },
    deleteButton: {
        backgroundColor: '#FF3B30',
        padding: 15,
        borderRadius: 8,
        alignItems: 'center',
        marginBottom: 20,
    },
    buttonText: {
        color: '#fff',
        fontWeight: 'bold',
        fontSize: 16,
    },
    deleteButtonText: {
        color: '#fff',
        fontWeight: 'bold',
        fontSize: 16,
    },
    switchModeButton: {
        padding: 15,
        alignItems: 'center',
    },
    switchModeText: {
        color: '#007AFF',
        fontSize: 14,
        textAlign: 'center',
    },
    infoSection: {
        marginBottom: 15,
        padding: 15,
        backgroundColor: '#f8f9fa',
        borderRadius: 8,
    },
    label: {
        fontSize: 12,
        color: '#666',
        marginBottom: 4,
    },
    value: {
        fontSize: 16,
        color: '#000',
        fontWeight: '500',
    },
});

export default ProfileScreen;