import {Text, View} from "react-native";
import {Link, useLocalSearchParams} from "expo-router";

const Details = () => {
    const {id} = useLocalSearchParams();
    return (
        <View className="flex-1 justify-center items-center">
            <Text className="text-5xl text-blue-500 font-bold">({id})</Text>

        </View>
    );
}

export default Details;