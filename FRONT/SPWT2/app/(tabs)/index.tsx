
import React, { useState, useEffect } from 'react';
import {
    View,
    Text,
    StyleSheet,
    Image,
    TouchableOpacity,
    ActivityIndicator,
    FlatList,
    RefreshControl
} from 'react-native';
import { Calendar } from 'react-native-calendars';
import { Ionicons } from '@expo/vector-icons';

interface RoutePhoto {
    photoId: number;
    photoLink: string;
    route: string;
}

interface Route {
    routeId: number;
    routePhotos: RoutePhoto[];
    name: string;
    description: string;
}

const Index = () => {
    const [routes, setRoutes] = useState<Route[]>([]);
    const [loading, setLoading] = useState(true);
    const [refreshing, setRefreshing] = useState(false);
    const [selectedDate, setSelectedDate] = useState('');

    // Заглушка для фильтров
    const filters = [
        { id: 'duration', label: 'Длительность' },
        { id: 'price', label: 'Цена' },
        { id: 'type', label: 'Тип катера' },
        { id: 'time', label: 'Время отправления' }
    ];

    // Загрузка маршрутов
    const fetchRoutes = async () => {
        try {
            setLoading(true);
            // Заглушка - потом замените на реальный API вызов
            const mockRoutes: Route[] = [
                {
                    routeId: 1,
                    name: "Ночная прогулка по Неве",
                    description: "Романтическая прогулка с видом на разводные мосты и достопримечательности Санкт-Петербурга",
                    routePhotos: [
                        {
                            photoId: 1,
                            photoLink: "https://images.unsplash.com/photo-1571019613454-1cb2f99b2d8b",
                            route: "string"
                        }
                    ]
                },
                {
                    routeId: 2,
                    name: "Обзорная экскурсия",
                    description: "Полная обзорная экскурсия по рекам и каналам города",
                    routePhotos: [
                        {
                            photoId: 2,
                            photoLink: "https://images.unsplash.com/photo-1534351590666-13e3e96b5017",
                            route: "string"
                        }
                    ]
                },
                {
                    routeId: 3,
                    name: "Утренний круиз",
                    description: "Свежий утренний воздух и прекрасные виды города без толп туристов",
                    routePhotos: [] // Тест без фото
                }
            ];

            // Имитация задержки сети
            await new Promise(resolve => setTimeout(resolve, 1000));
            setRoutes(mockRoutes);

        } catch (error) {
            console.error('Error fetching routes:', error);
        } finally {
            setLoading(false);
            setRefreshing(false);
        }
    };

    useEffect(() => {
        fetchRoutes();
    }, []);

    const onRefresh = () => {
        setRefreshing(true);
        fetchRoutes();
    };

    // Верхняя часть с календарем и фильтрами
    const renderHeader = () => (
        <View style={styles.header}>
            {/* Отступ сверху */}
            <View style={styles.topSpacer} />

            {/* Календарь */}
            <View style={styles.calendarSection}>
                <Text style={styles.sectionTitle}>Выберите дату</Text>
                <Calendar
                    current={new Date().toISOString().split('T')[0]}
                    minDate={new Date().toISOString().split('T')[0]}
                    onDayPress={(day) => setSelectedDate(day.dateString)}
                    markedDates={{
                        [selectedDate]: { selected: true, selectedColor: '#007AFF' }
                    }}
                    theme={{
                        selectedDayBackgroundColor: '#007AFF',
                        todayTextColor: '#007AFF',
                        arrowColor: '#007AFF',
                    }}
                    style={styles.calendar}
                />
            </View>

            {/* Фильтры */}
            <View style={styles.filtersSection}>
                <Text style={styles.sectionTitle}>Фильтры</Text>
                <FlatList
                    horizontal
                    data={filters}
                    renderItem={({ item }) => (
                        <TouchableOpacity key={item.id} style={styles.filterButton}>
                            <Text style={styles.filterText}>{item.label}</Text>
                        </TouchableOpacity>
                    )}
                    keyExtractor={item => item.id}
                    showsHorizontalScrollIndicator={false}
                    contentContainerStyle={styles.filtersList}
                />
            </View>

            {/* Заголовок маршрутов */}
            <View style={styles.routesHeader}>
                <Text style={styles.sectionTitle}>Доступные маршруты</Text>
            </View>
        </View>
    );

    // Карточка маршрута
    const renderRouteCard = ({ item }: { item: Route }) => (
        <View style={styles.card}>
            {/* Картинка маршрута */}
            <View style={styles.imageContainer}>
                {item.routePhotos.length > 0 && item.routePhotos[0].photoLink ? (
                    <Image
                        source={{ uri: item.routePhotos[0].photoLink }}
                        style={styles.image}
                        onError={() => console.log('Image load failed')}
                    />
                ) : (
                    <View style={styles.noImage}>
                        <Ionicons name="image-outline" size={40} color="#666" />
                        <Text style={styles.noImageText}>Нет фото</Text>
                    </View>
                )}
            </View>

            {/* Контент карточки */}
            <View style={styles.cardContent}>
                <Text style={styles.routeName}>{item.name}</Text>
                <Text style={styles.routeDescription} numberOfLines={3}>
                    {item.description}
                </Text>

                <TouchableOpacity style={styles.detailsButton}>
                    <Text style={styles.detailsButtonText}>Подробнее</Text>
                </TouchableOpacity>
            </View>
        </View>
    );

    if (loading) {
        return (
            <View style={styles.center}>
                <ActivityIndicator size="large" color="#007AFF" />
                <Text style={styles.loadingText}>Загрузка маршрутов...</Text>
            </View>
        );
    }

    return (
        <View style={styles.container}>
            <FlatList
                data={routes}
                renderItem={renderRouteCard}
                keyExtractor={item => item.routeId.toString()}
                ListHeaderComponent={renderHeader}
                refreshControl={
                    <RefreshControl refreshing={refreshing} onRefresh={onRefresh} />
                }
                showsVerticalScrollIndicator={false}
                contentContainerStyle={styles.listContent}
                ListEmptyComponent={
                    <View style={styles.emptyState}>
                        <Ionicons name="boat-outline" size={60} color="#ccc" />
                        <Text style={styles.emptyStateText}>Маршруты не найдены</Text>
                    </View>
                }
                stickyHeaderIndices={[]}
            />
        </View>
    );
};

const styles = StyleSheet.create({
    container: {
        flex: 1,
        backgroundColor: '#f8f9fa',
    },
    center: {
        flex: 1,
        justifyContent: 'center',
        alignItems: 'center',
        backgroundColor: '#f8f9fa',
    },
    loadingText: {
        marginTop: 10,
        color: '#666',
        fontSize: 16,
    },
    header: {
        backgroundColor: '#f8f9fa',
    },
    topSpacer: {
        height: 16,
    },
    calendarSection: {
        backgroundColor: '#fff',
        padding: 16,
        marginHorizontal: 16,
        borderRadius: 12,
        marginBottom: 16,
        shadowColor: '#000',
        shadowOffset: { width: 0, height: 2 },
        shadowOpacity: 0.1,
        shadowRadius: 4,
        elevation: 3,
    },
    filtersSection: {
        backgroundColor: '#fff',
        padding: 16,
        marginHorizontal: 16,
        borderRadius: 12,
        marginBottom: 16,
        shadowColor: '#000',
        shadowOffset: { width: 0, height: 2 },
        shadowOpacity: 0.1,
        shadowRadius: 4,
        elevation: 3,
    },
    filtersList: {
        paddingVertical: 4,
    },
    routesHeader: {
        backgroundColor: '#fff',
        padding: 16,
        marginHorizontal: 16,
        borderRadius: 12,
        marginBottom: 16,
        shadowColor: '#000',
        shadowOffset: { width: 0, height: 2 },
        shadowOpacity: 0.1,
        shadowRadius: 4,
        elevation: 3,
    },
    sectionTitle: {
        fontSize: 18,
        fontWeight: 'bold',
        marginBottom: 12,
        color: '#000',
    },
    calendar: {
        borderRadius: 10,
        overflow: 'hidden',
    },
    filterButton: {
        paddingHorizontal: 16,
        paddingVertical: 8,
        backgroundColor: '#f1f1f1',
        borderRadius: 20,
        marginRight: 8,
        marginVertical: 4,
    },
    filterText: {
        color: '#333',
        fontSize: 14,
    },
    listContent: {
        paddingBottom: 20,
    },
    card: {
        backgroundColor: '#fff',
        borderRadius: 12,
        marginHorizontal: 16,
        marginBottom: 16,
        shadowColor: '#000',
        shadowOffset: { width: 0, height: 2 },
        shadowOpacity: 0.1,
        shadowRadius: 4,
        elevation: 3,
        overflow: 'hidden',
    },
    imageContainer: {
        width: '100%',
        height: 200,
    },
    image: {
        width: '100%',
        height: '100%',
    },
    noImage: {
        width: '100%',
        height: '100%',
        backgroundColor: '#f8f9fa',
        justifyContent: 'center',
        alignItems: 'center',
    },
    noImageText: {
        marginTop: 8,
        color: '#666',
        fontSize: 14,
    },
    cardContent: {
        padding: 16,
    },
    routeName: {
        fontSize: 18,
        fontWeight: 'bold',
        marginBottom: 8,
        color: '#000',
    },
    routeDescription: {
        fontSize: 14,
        color: '#666',
        lineHeight: 20,
        marginBottom: 12,
    },
    detailsButton: {
        backgroundColor: '#007AFF',
        paddingVertical: 10,
        paddingHorizontal: 20,
        borderRadius: 8,
        alignSelf: 'flex-start',
    },
    detailsButtonText: {
        color: '#fff',
        fontWeight: '600',
        fontSize: 14,
    },
    emptyState: {
        alignItems: 'center',
        justifyContent: 'center',
        padding: 40,
        marginTop: 20,
    },
    emptyStateText: {
        marginTop: 16,
        color: '#666',
        fontSize: 16,
        textAlign: 'center',
    },
});

export default Index;