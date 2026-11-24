import SwiftUI
import Playgrounds

struct ContentView: View {
    var body: some View {
        VStack {
            VStack {
                Text("Calculator Cen")
                    .font(.title)
            }
            .frame(minWidth: .infinity)
            .background(.red)

            Spacer()
        }
    }
}

#Preview {
    ContentView()
}

#Playground {
    _ = 1 + 2
}
