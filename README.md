[![hh-banner-small](https://github.com/Rahkeen/HydroHomie/assets/2228493/c5789617-5829-455b-82ba-eb48df38acbc)](https://www.hydrohomie.co)

| Drink  | Set | Track |
| ------------- | ------------- | ------------ |
| ![App Screenshot - Drink](https://github.com/Rahkeen/HydroHomie/assets/2228493/298b6674-c69d-4aa2-846f-7c2ebdec1bce) | ![App Screenshot - Set](https://github.com/Rahkeen/HydroHomie/assets/2228493/7ce6047a-0891-4298-b8ef-7de759d58409) | ![App Screenshot - Track](https://github.com/Rahkeen/HydroHomie/assets/2228493/8842cf6e-9193-4171-853c-be64277fe2d2) |

## Navigating

Hydro Homie is a Compose app built with [Mavericks](https://airbnb.io/mavericks/#/)

In order to navigate the codebase, take a look at the directory structure:

<img width="270" alt="Screenshot 2023-11-28 at 12 07 49 PM" src="https://github.com/Rahkeen/HydroHomie/assets/2228493/2225376f-918c-4428-a07c-c8fef0764b09">

Each screen is setup in it's own feature package, which is usually split up into the `domain` and the `surface`
- `domain` is usually any UI state classes needed to help render the screen
- `surface` containts the screen entrypoint, which will also hold a full preview of the screen as well as any specific components

Data dependencies are wrapped with a `Repository`, but it's really just an interface that sits on top.

The core entry point is in `MavericksApp` and feel free to disregard any `workflow` components as they are remnants of using [Workflow](https://square.github.io/workflow/). If you are curious, this repo was used to compare architecture libraries which I talked about at [DroidconSF 2022](https://www.droidcon.com/2022/06/28/state-machines-and-hopeful-dreams/)


